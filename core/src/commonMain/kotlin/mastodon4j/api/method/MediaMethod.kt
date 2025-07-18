package mastodon4j.api.method

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
     * メディアファイルをアップロード
     * POST /api/v1/media
     * 
     * 注意: この実装は基本的なアップロード機能のみを提供します。
     * 実際のファイルアップロードについては、プラットフォーム固有の
     * 実装（androidMain、iosMain等）で詳細を実装してください。
     * 
     * @param description メディアの説明（オプション）
     * @param focus フォーカス座標（オプション）
     */
    fun postMedia(
        description: String? = null,
        focus: String? = null
    ): MastodonRequest<MediaAttachment> {
        // KMP環境では、実際のファイルアップロードは
        // プラットフォーム固有の実装で行う必要があります
        return client.createPostRequest<MediaAttachment>("/api/v1/media")
    }
}