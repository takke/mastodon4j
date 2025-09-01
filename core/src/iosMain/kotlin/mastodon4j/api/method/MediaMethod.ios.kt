package mastodon4j.api.method

import mastodon4j.MastodonRequest
import mastodon4j.api.entity.MediaAttachment

/**
 * Android固有のMediaMethod拡張関数実装
 */

/**
 * メディアファイルをアップロード
 * POST /api/v1/media
 */
actual fun MediaMethod.postMedia(
    file: Any,
    mimeType: String,
    description: String?,
    focus: String?
): MastodonRequest<MediaAttachment> {
    // TODO 実装すること
    throw NotImplementedError("This function is not implemented yet")
//    require(file is File) { "file must be a File instance on Android" }
//
//    val client = this.getClient()
//
//    return MastodonRequest(
//        {
//            client.getHttpClient().submitFormWithBinaryData(
//                url = "${client.baseUrl}/api/v1/media",
//                formData = formData {
//                    append("file", file.readBytes(), Headers.build {
//                        append(HttpHeaders.ContentType, mimeType)
//                        append(HttpHeaders.ContentDisposition, "filename=\"${file.name}\"")
//                    })
//                    description?.let { append("description", it) }
//                    focus?.let { append("focus", it) }
//                }
//            )
//        },
//        { responseText ->
//            client.json.decodeFromString<MediaAttachment>(responseText)
//        }
//    )
}