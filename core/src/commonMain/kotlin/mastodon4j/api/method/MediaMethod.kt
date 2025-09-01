package mastodon4j.api.method

import androidx.annotation.CheckResult
import io.ktor.client.request.forms.*
import io.ktor.http.*
import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.entity.MediaAttachment

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
    @CheckResult
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

    /**
     * メディアファイルをアップロード
     * POST /api/v1/media
     *
     * @param bytes アップロードするファイルのバイト配列
     * @param fileName ファイル名
     * @param mimeType MIMEタイプ
     * @param description メディアの説明
     * @param focus フォーカスポイント
     */
    fun postMedia(
        bytes: ByteArray,
        fileName: String,
        mimeType: String,
        description: String? = null,
        focus: String? = null
    ): MastodonRequest<MediaAttachment> {
        val client = this.getClient()

        return MastodonRequest(
            {
                client.getHttpClient().submitFormWithBinaryData(
                    url = "${client.baseUrl}/api/v1/media",
                    formData = formData {
                        append("file", bytes, Headers.build {
                            append(HttpHeaders.ContentType, mimeType)
                            append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                        })
                        description?.let { append("description", it) }
                        focus?.let { append("focus", it) }
                    }
                )
            },
            { responseText ->
                client.json.decodeFromString<MediaAttachment>(responseText)
            }
        )
    }
}

