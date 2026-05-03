package mastodon4j.api.method

import io.ktor.client.plugins.HttpTimeoutConfig
import io.ktor.client.plugins.timeout
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import mastodon4j.MastodonClient
import mastodon4j.api.StreamingEvent
import mastodon4j.api.StreamingFrame
import mastodon4j.api.entity.Announcement
import mastodon4j.api.entity.Conversation
import mastodon4j.api.entity.Notification
import mastodon4j.api.entity.Status
import mastodon4j.api.exception.MastodonStreamingException

/**
 * Mastodon Streaming API クライアント（WebSocket版）
 *
 * 各メソッドは [Flow] を返し、collect すると WebSocket 接続を確立して
 * イベントを順次 emit する。collect が cancel されると WebSocket も切断される。
 *
 * 再接続ロジックは含まれない。呼び出し側で `retryWhen` 等を使って実装すること。
 *
 * 認証は [MastodonClient.Builder.accessToken] で設定された Bearer トークンを利用する。
 *
 * see more https://docs.joinmastodon.org/methods/streaming/
 */
class Streaming(private val client: MastodonClient) {

    /**
     * ユーザーストリーム（ホームタイムライン + 通知 + その他）
     */
    fun user(): Flow<StreamingEvent> = connect("user")

    /**
     * 通知のみのユーザーストリーム（Mastodon 4.0+）
     *
     * `stream=user:notification` を指定すると通知のみが流れる軽量ストリーム。
     * バックエンドが対応していない場合は [user] と同じ挙動になる可能性がある。
     */
    fun userNotification(): Flow<StreamingEvent> = connect("user:notification")

    /**
     * 連合タイムライン
     */
    fun publicFederated(): Flow<StreamingEvent> = connect("public")

    /**
     * ローカルタイムライン
     */
    fun publicLocal(): Flow<StreamingEvent> = connect("public:local")

    /**
     * リモートのみの連合タイムライン
     */
    fun publicRemote(): Flow<StreamingEvent> = connect("public:remote")

    /**
     * ハッシュタグストリーム（連合）
     */
    fun hashtag(tag: String): Flow<StreamingEvent> =
        connect("hashtag", mapOf("tag" to tag))

    /**
     * ハッシュタグストリーム（ローカル）
     */
    fun hashtagLocal(tag: String): Flow<StreamingEvent> =
        connect("hashtag:local", mapOf("tag" to tag))

    /**
     * リストタイムライン
     */
    fun list(listId: String): Flow<StreamingEvent> =
        connect("list", mapOf("list" to listId))

    /**
     * ダイレクトメッセージ
     */
    fun direct(): Flow<StreamingEvent> = connect("direct")

    /**
     * 任意のストリーム名で接続（将来追加されるストリーム用エスケープハッチ）
     */
    fun stream(streamName: String, additionalParams: Map<String, String> = emptyMap()): Flow<StreamingEvent> =
        connect(streamName, additionalParams)

    private fun connect(streamName: String, additionalParams: Map<String, String> = emptyMap()): Flow<StreamingEvent> = flow {
        val httpClient = client.getHttpClient()

        httpClient.webSocket(
            request = {
                // WebSocket 接続では、MastodonClient のグローバル HttpTimeout
                // （socketTimeoutMillis=60秒）が長時間アイドル時に接続を切ってしまうため、
                // ストリーム特有の長時間接続を許容するように socket / request timeout を無効化する。
                // ネットワーク断は incoming channel のクローズで検出する。
                timeout {
                    socketTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
                    requestTimeoutMillis = HttpTimeoutConfig.INFINITE_TIMEOUT_MS
                }
                url {
                    protocol = URLProtocol.WSS
                    host = client.getInstanceName()
                    encodedPath = "/api/v1/streaming"
                    parameters.append("stream", streamName)
                    additionalParams.forEach { (k, v) -> parameters.append(k, v) }
                }
            }
        ) {
            for (frame in incoming) {
                if (frame !is Frame.Text) continue
                val event = parseFrame(frame.readText(), client.json) ?: continue
                emit(event)
            }
            // for ループ抜け = サーバー側 close。CloseReason を確認し、
            // NORMAL / GOING_AWAY 以外（認証切れ・ポリシー違反など）は例外として伝播する。
            val reason = closeReason.await()
            if (reason != null && !isNormalClose(reason)) {
                throw MastodonStreamingException(reason)
            }
        }
    }

    private fun isNormalClose(reason: CloseReason): Boolean {
        return reason.code == CloseReason.Codes.NORMAL.code ||
            reason.code == CloseReason.Codes.GOING_AWAY.code
    }

    private fun parseFrame(text: String, json: Json): StreamingEvent? {
        val frame = runCatching { json.decodeFromString(StreamingFrame.serializer(), text) }
            .getOrNull() ?: return null

        return when (frame.event) {
            "update" -> frame.decodeAs<Status>(json)?.let { StreamingEvent.Update(it) }
            "status.update" -> frame.decodeAs<Status>(json)?.let { StreamingEvent.StatusUpdate(it) }
            "delete" -> StreamingEvent.Delete(frame.payload)
            "notification" -> frame.decodeAs<Notification>(json)?.let { StreamingEvent.NewNotification(it) }
            "conversation" -> frame.decodeAs<Conversation>(json)?.let { StreamingEvent.ConversationUpdate(it) }
            "announcement" -> frame.decodeAs<Announcement>(json)?.let { StreamingEvent.AnnouncementUpdate(it) }
            "announcement.reaction" -> StreamingEvent.AnnouncementReaction(frame.payload)
            "announcement.delete" -> StreamingEvent.AnnouncementDelete(frame.payload)
            "filters_changed" -> StreamingEvent.FiltersChanged
            "encrypted_message" -> StreamingEvent.EncryptedMessage(frame.payload)
            else -> StreamingEvent.Unknown(frame.event, frame.payload)
        }
    }

    private inline fun <reified T> StreamingFrame.decodeAs(json: Json): T? =
        runCatching { json.decodeFromString<T>(payload) }.getOrNull()
}
