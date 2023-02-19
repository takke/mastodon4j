package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Scope
import mastodon4j.api.entity.auth.AccessToken
import mastodon4j.api.entity.auth.AppRegistration
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#apps
 */
class AppsMethod(private val client: MastodonClient) {
    // POST /api/v1/apps
    @JvmOverloads
    fun createApp(
        clientName: String,
        redirectUris: String = "urn:ietf:wg:oauth:2.0:oob",
        scope: Scope = Scope(Scope.Name.ALL),
        website: String? = null
    ): MastodonRequest<AppRegistration> {
        scope.validate()
        return MastodonRequest(
            {
                client.post(
                    "/api/v1/apps",
                    RequestBody.create(
                        "application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull(),
                        arrayListOf(
                            "client_name=$clientName",
                            "scopes=$scope",
                            "redirect_uris=$redirectUris"
                        ).apply {
                            website?.let {
                                add("website=${it}")
                            }
                        }.joinToString(separator = "&")
                    )
                )
            },
            {
                client.getSerializer().fromJson(it, AppRegistration::class.java).apply {
                    this.instanceName = client.getInstanceName()
                }
            }
        )
    }

    fun getOAuthUrl(clientId: String, scope: Scope, redirectUri: String = "urn:ietf:wg:oauth:2.0:oob"): String {
        val endpoint = "/oauth/authorize"
        val parameters = listOf(
            "client_id=$clientId",
            "redirect_uri=$redirectUri",
            "response_type=code",
            "scope=$scope"
        ).joinToString(separator = "&")
        return "https://${client.getInstanceName()}$endpoint?$parameters"
    }

    // POST /oauth/token
    @JvmOverloads
    fun getAccessToken(
        clientId: String,
        clientSecret: String,
        redirectUri: String = "urn:ietf:wg:oauth:2.0:oob",
        code: String,
        grantType: String = "authorization_code"
    ): MastodonRequest<AccessToken> {
        val parameters = listOf(
            "client_id=$clientId",
            "client_secret=$clientSecret",
            "redirect_uri=$redirectUri",
            "code=$code",
            "grant_type=$grantType"
        ).joinToString(separator = "&")
        return MastodonRequest(
            {
                client.post(
                    "/oauth/token",
                    RequestBody.create(
                        "application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull(),
                        parameters
                    )
                )
            },
            {
                client.getSerializer().fromJson(it, AccessToken::class.java)
            }
        )
    }

    // POST /oauth/token
    fun postUserNameAndPassword(
        clientId: String,
        clientSecret: String,
        scope: Scope,
        userName: String,
        password: String
    ): MastodonRequest<AccessToken> {
        val parameters = Parameter().apply {
            append("client_id", clientId)
            append("client_secret", clientSecret)
            append("scope", scope.toString())
            append("username", userName)
            append("password", password)
            append("grant_type", "password")
        }.build()

        return MastodonRequest(
            {
                client.post(
                    "/oauth/token",
                    RequestBody.create(
                        "application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull(),
                        parameters
                    )
                )
            },
            {
                client.getSerializer().fromJson(it, AccessToken::class.java)
            }
        )
    }
}
