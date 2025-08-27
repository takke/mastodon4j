package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.entity.MediaAttachment
import org.slf4j.helpers.CheckReturnValue

/**
 * メディアに関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#media
 * 
 * 注意: KMP対応では、プラットフォーム固有のマルチパートアップロード機能は
 * プラットフォーム固有のモジュールで実装する必要があります。
 */
class MediaMethod(private val client: MastodonClient) {
    
    /**
     * プラットフォーム固有の実装でクライアントにアクセスするためのinternal getter
     */
    internal fun getClient(): MastodonClient = client

    /**
     * メディア情報を取得
     * GET /api/v1/media/:id
     */
    fun getMedia(mediaId: String): MastodonRequest<MediaAttachment> {
        return client.createGetRequest<MediaAttachment>("/api/v1/media/$mediaId")
    }

    /**
     * メディア情報を更新
     * PUT /api/v1/media/:id
     */
    @CheckReturnValue
    fun putMedia(
        mediaId: String,
        description: String? = null,
        focus: String? = null
    ): MastodonRequest<MediaAttachment> {
        val params = mastodon4j.Parameter().apply {
            description?.let { append("description", it) }
            focus?.let { append("focus", it) }
        }
        return client.createPutRequest<MediaAttachment>("/api/v1/media/$mediaId", params)
    }
}

/**
 * プラットフォーム固有のファイルアップロード関連拡張関数
 */
expect fun MediaMethod.postMedia(
    file: Any,
    mimeType: String,
    description: String? = null,
    focus: String? = null
): MastodonRequest<MediaAttachment>