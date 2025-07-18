package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Scope
import mastodon4j.api.entity.auth.AccessToken
import mastodon4j.api.entity.auth.AppRegistration

/**
 * アプリケーション関連のAPIメソッドクラス（KMP対応版）
 * 
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#apps
 */
class AppsMethod(private val client: MastodonClient) {

    /**
     * アプリケーションを作成
     * POST /api/v1/apps
     */
    fun createApp(
        clientName: String,
        redirectUris: String = "urn:ietf:wg:oauth:2.0:oob",
        scope: Scope = Scope(Scope.Name.ALL),
        website: String? = null
    ): MastodonRequest<AppRegistration> {
        scope.validate()
        
        val params = Parameter().apply {
            append("client_name", clientName)
            append("scopes", scope.toString())
            append("redirect_uris", redirectUris)
            website?.let { append("website", it) }
        }
        
        return client.createPostRequest<AppRegistration>("/api/v1/apps", params)
    }

    /**
     * OAuth認証URLを生成
     */
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

    /**
     * アクセストークンを取得
     * POST /oauth/token
     */
    fun getAccessToken(
        clientId: String,
        clientSecret: String,
        redirectUri: String = "urn:ietf:wg:oauth:2.0:oob",
        code: String,
        grantType: String = "authorization_code",
        scope: Scope = Scope(Scope.Name.ALL)
    ): MastodonRequest<AccessToken> {
        val params = Parameter().apply {
            append("client_id", clientId)
            append("client_secret", clientSecret)
            append("scope", scope.toString())
            append("redirect_uri", redirectUri)
            append("code", code)
            append("grant_type", grantType)
        }

        return client.createPostRequest<AccessToken>("/oauth/token", params)
    }

    /**
     * ユーザー名とパスワードでアクセストークンを取得
     * POST /oauth/token
     */
    fun postUserNameAndPassword(
        clientId: String,
        clientSecret: String,
        scope: Scope,
        userName: String,
        password: String
    ): MastodonRequest<AccessToken> {
        val params = Parameter().apply {
            append("client_id", clientId)
            append("client_secret", clientSecret)
            append("scope", scope.toString())
            append("username", userName)
            append("password", password)
            append("grant_type", "password")
        }

        return client.createPostRequest<AccessToken>("/oauth/token", params)
    }
}