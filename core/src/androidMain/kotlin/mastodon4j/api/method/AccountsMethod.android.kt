package mastodon4j.api.method

import mastodon4j.MastodonRequest
import mastodon4j.api.entity.CredentialAccount
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * Android固有のAccountsMethod拡張関数実装
 */

/**
 * アバター画像を更新
 * PATCH /api/v1/accounts/update_credentials
 */
actual fun AccountsMethod.updateAvatar(avatarFile: Any, mimeType: String): MastodonRequest<CredentialAccount> {
    require(avatarFile is File) { "avatarFile must be a File instance on Android" }
    
    val client = this.getClient()
    
    return MastodonRequest(
        {
            client.patch(
                "/api/v1/accounts/update_credentials",
                MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "avatar", avatarFile.name,
                        avatarFile.asRequestBody(mimeType.toMediaType())
                    )
                    .build()
            )
        },
        {
            client.getSerializer().fromJson(it, CredentialAccount::class.java)
        }
    )
}

/**
 * ヘッダー画像を更新
 * PATCH /api/v1/accounts/update_credentials
 */
actual fun AccountsMethod.updateHeader(headerFile: Any, mimeType: String): MastodonRequest<CredentialAccount> {
    require(headerFile is File) { "headerFile must be a File instance on Android" }
    
    val client = this.getClient()
    
    return MastodonRequest(
        {
            client.patch(
                "/api/v1/accounts/update_credentials",
                MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "header", headerFile.name,
                        headerFile.asRequestBody(mimeType.toMediaType())
                    )
                    .build()
            )
        },
        {
            client.getSerializer().fromJson(it, CredentialAccount::class.java)
        }
    )
}