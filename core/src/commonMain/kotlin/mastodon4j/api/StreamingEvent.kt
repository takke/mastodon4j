package mastodon4j.api

import mastodon4j.api.entity.Announcement
import mastodon4j.api.entity.Conversation
import mastodon4j.api.entity.Notification
import mastodon4j.api.entity.Status

/**
 * Mastodon Streaming API から受信するイベント
 *
 * see more https://docs.joinmastodon.org/methods/streaming/#events
 */
sealed class StreamingEvent {

    /**
     * WebSocket ハンドシェイクが完了し、ストリーム接続が確立された瞬間に一度だけ emit される擬似イベント。
     *
     * Mastodon サーバーが送るフレームではなく、クライアント (mastodon4j) 側で生成して通知する。
     * 「接続中」UI 表示や Backfill リロード等を「実際に接続できたタイミング」で発火させたい場合に利用する。
     */
    object Connected : StreamingEvent()

    /**
     * 新しい投稿（タイムライン更新）
     * event: "update"
     */
    data class Update(val status: Status) : StreamingEvent()

    /**
     * 投稿の編集
     * event: "status.update"
     */
    data class StatusUpdate(val status: Status) : StreamingEvent()

    /**
     * 投稿の削除
     * event: "delete"
     * payload: 削除された投稿ID（文字列）
     */
    data class Delete(val statusId: String) : StreamingEvent()

    /**
     * 通知
     * event: "notification"
     */
    data class NewNotification(val notification: Notification) : StreamingEvent()

    /**
     * ダイレクト会話の更新
     * event: "conversation"
     */
    data class ConversationUpdate(val conversation: Conversation) : StreamingEvent()

    /**
     * 新しいアナウンス
     * event: "announcement"
     */
    data class AnnouncementUpdate(val announcement: Announcement) : StreamingEvent()

    /**
     * アナウンスへのリアクション追加・削除
     * event: "announcement.reaction"
     */
    data class AnnouncementReaction(val payload: String) : StreamingEvent()

    /**
     * アナウンスの削除
     * event: "announcement.delete"
     * payload: 削除されたアナウンスID
     */
    data class AnnouncementDelete(val announcementId: String) : StreamingEvent()

    /**
     * フィルター変更通知（ペイロードなし）
     * event: "filters_changed"
     */
    object FiltersChanged : StreamingEvent()

    /**
     * 暗号化メッセージ（E2EE関連）
     * event: "encrypted_message"
     */
    data class EncryptedMessage(val payload: String) : StreamingEvent()

    /**
     * 既知のイベント名以外、もしくはパース失敗時のフォールバック
     */
    data class Unknown(val event: String, val payload: String) : StreamingEvent()
}
