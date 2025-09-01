package mastodon4j.api.method

import mastodon4j.MastodonRequest
import mastodon4j.api.entity.CredentialAccount

/**
 * Android固有のAccountsMethod拡張関数実装
 */

/**
 * アバター画像を更新
 * PATCH /api/v1/accounts/update_credentials
 */
actual fun AccountsMethod.updateAvatar(avatarFile: Any, mimeType: String): MastodonRequest<CredentialAccount> {
    // TODO 実装すること
    throw NotImplementedError("This function is not implemented yet")
//    require(avatarFile is File) { "avatarFile must be a File instance on Android" }
//
//    val client = this.getClient()
//
//    return MastodonRequest(
//        {
//            client.getHttpClient().submitFormWithBinaryData(
//                url = "${client.baseUrl}/api/v1/accounts/update_credentials",
//                formData = formData {
//                    append("avatar", avatarFile.readBytes(), Headers.build {
//                        append(HttpHeaders.ContentType, mimeType)
//                        append(HttpHeaders.ContentDisposition, "filename=\"${avatarFile.name}\"")
//                    })
//                }
//            ) {
//                method = HttpMethod.Patch
//            }
//        },
//        { responseText ->
//            client.json.decodeFromString<CredentialAccount>(responseText)
//        }
//    )
}

/**
 * ヘッダー画像を更新
 * PATCH /api/v1/accounts/update_credentials
 */
actual fun AccountsMethod.updateHeader(headerFile: Any, mimeType: String): MastodonRequest<CredentialAccount> {
    // TODO 実装すること
    throw NotImplementedError("This function is not implemented yet")
//    require(headerFile is File) { "headerFile must be a File instance on Android" }
//
//    val client = this.getClient()
//
//    return MastodonRequest(
//        {
//            client.getHttpClient().submitFormWithBinaryData(
//                url = "${client.baseUrl}/api/v1/accounts/update_credentials",
//                formData = formData {
//                    append("header", headerFile.readBytes(), Headers.build {
//                        append(HttpHeaders.ContentType, mimeType)
//                        append(HttpHeaders.ContentDisposition, "filename=\"${headerFile.name}\"")
//                    })
//                }
//            ) {
//                method = HttpMethod.Patch
//            }
//        },
//        { responseText ->
//            client.json.decodeFromString<CredentialAccount>(responseText)
//        }
//    )
}