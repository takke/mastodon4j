package mastodon4j.api.method

import androidx.annotation.CheckResult
import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.*
import mastodon4j.api.exception.MastodonException

/**
 * Accountsに関するAPIメソッドクラス（KMP対応版）
 *
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#accounts
 */
class AccountsMethod(private val client: MastodonClient) {

    /**
     * プラットフォーム固有の実装でクライアントにアクセスするためのinternal getter
     */
    internal fun getClient(): MastodonClient = client

    /**
     * 指定されたIDのアカウント情報を取得
     * GET /api/v1/accounts/:id
     */
    fun getAccount(accountId: String): MastodonRequest<Account> {
        return client.createGetRequest<Account>("/api/v1/accounts/$accountId")
    }

    /**
     * acctからアカウントを検索
     * GET /api/v1/accounts/lookup
     */
    fun lookup(acct: String): MastodonRequest<Account> {
        val params = Parameter().append("acct", acct)
        return client.createGetRequest<Account>("/api/v1/accounts/lookup", params)
    }

    /**
     * 認証情報を確認
     * GET /api/v1/accounts/verify_credentials
     */
    fun getVerifyCredentials(): MastodonRequest<CredentialAccount> {
        return client.createGetRequest<CredentialAccount>("/api/v1/accounts/verify_credentials")
    }

    /**
     * アカウント情報を更新
     * PATCH /api/v1/accounts/update_credentials
     */
    @CheckResult
    fun updateCredentials(
        displayName: String? = null,
        note: String? = null,
        locked: Boolean? = null,
        bot: Boolean? = null,
        discoverable: Boolean? = null,
        fieldsAttributesNames: List<String>? = null,
        fieldsAttributesValues: List<String>? = null
    ): MastodonRequest<CredentialAccount> {
        // フィールド名と値のリストサイズが一致することを確認
        if (fieldsAttributesNames != null && fieldsAttributesValues != null) {
            if (fieldsAttributesNames.size != fieldsAttributesValues.size) {
                throw MastodonException("fieldsAttributesNames.size != fieldsAttributesValues.size")
            }
        }

        val params = Parameter().apply {
            displayName?.let { append("display_name", it) }
            note?.let { append("note", it) }
            locked?.let { append("locked", it) }
            bot?.let { append("bot", it) }
            discoverable?.let { append("discoverable", it) }

            // フィールド属性の設定
            if (fieldsAttributesNames != null && fieldsAttributesValues != null) {
                for (i in fieldsAttributesNames.indices) {
                    append("fields_attributes[$i][name]", fieldsAttributesNames[i])
                    append("fields_attributes[$i][value]", fieldsAttributesValues[i])
                }
            }
        }

        return client.createPatchRequest<CredentialAccount>("/api/v1/accounts/update_credentials", params)
    }

    /**
     * アカウントのフォロワーリストを取得
     * GET /api/v1/accounts/:id/followers
     */
    fun getFollowers(accountId: String, range: Range? = null): MastodonRequest<Pageable<Account>> {
        return client.createListGetRequest<Account>(
            path = "/api/v1/accounts/$accountId/followers",
            parameters = range?.toParameter()
        ).toPageable()
    }

    /**
     * アカウントのフォローリストを取得
     * GET /api/v1/accounts/:id/following
     */
    fun getFollowing(accountId: String, range: Range? = null): MastodonRequest<Pageable<Account>> {
        return client.createListGetRequest<Account>(
            path = "/api/v1/accounts/$accountId/following",
            parameters = range?.toParameter()
        ).toPageable()
    }

    /**
     * アカウントのステータスリストを取得
     * GET /api/v1/accounts/:id/statuses
     */
    fun getStatuses(
        accountId: String,
        onlyMedia: Boolean? = false,
        excludeReplies: Boolean? = false,
        pinned: Boolean? = false,
        range: Range? = Range()
    ): MastodonRequest<Pageable<Status>> {
        val params = range?.toParameter() ?: Parameter()
        onlyMedia?.let { if (it) params.append("only_media", it) }
        excludeReplies?.let { if (it) params.append("exclude_replies", it) }
        pinned?.let { if (it) params.append("pinned", it) }

        return client.createListGetRequest<Status>("/api/v1/accounts/$accountId/statuses", params).toPageable()
    }

    /**
     * アカウントをフォロー
     * POST /api/v1/accounts/:id/follow
     */
    @CheckResult
    fun postFollow(accountId: String, reblogs: Boolean? = null, notify: Boolean? = null): MastodonRequest<Relationship> {
        val params = Parameter().apply {
            reblogs?.let { append("reblogs", it) }
            notify?.let { append("notify", it) }
        }
        return client.createPostRequest<Relationship>("/api/v1/accounts/$accountId/follow", params)
    }

    /**
     * アカウントのフォローを解除
     * POST /api/v1/accounts/:id/unfollow
     */
    @CheckResult
    fun postUnfollow(accountId: String): MastodonRequest<Relationship> {
        return client.createPostRequest<Relationship>("/api/v1/accounts/$accountId/unfollow")
    }

    /**
     * アカウントをブロック
     * POST /api/v1/accounts/:id/block
     */
    @CheckResult
    fun postBlock(accountId: String): MastodonRequest<Relationship> {
        return client.createPostRequest<Relationship>("/api/v1/accounts/$accountId/block")
    }

    /**
     * アカウントのブロックを解除
     * POST /api/v1/accounts/:id/unblock
     */
    @CheckResult
    fun postUnblock(accountId: String): MastodonRequest<Relationship> {
        return client.createPostRequest<Relationship>("/api/v1/accounts/$accountId/unblock")
    }

    /**
     * アカウントをミュート
     * POST /api/v1/accounts/:id/mute
     */
    @CheckResult
    fun postMute(accountId: String, notifications: Boolean? = null, duration: Int? = null): MastodonRequest<Relationship> {
        val params = Parameter().apply {
            notifications?.let { append("notifications", it) }
            duration?.let { append("duration", it) }
        }
        return client.createPostRequest<Relationship>("/api/v1/accounts/$accountId/mute", params)
    }

    /**
     * アカウントのミュートを解除
     * POST /api/v1/accounts/:id/unmute
     */
    @CheckResult
    fun postUnmute(accountId: String): MastodonRequest<Relationship> {
        return client.createPostRequest<Relationship>("/api/v1/accounts/$accountId/unmute")
    }

    /**
     * 複数アカウントとの関係を取得
     * GET /api/v1/accounts/relationships
     */
    fun getRelationships(accountIds: List<String>): MastodonRequest<List<Relationship>> {
        val params = Parameter().append("id", accountIds)
        return client.createListGetRequest<Relationship>("/api/v1/accounts/relationships", params)
    }

    /**
     * アカウントを検索
     * GET /api/v1/accounts/search
     */
    fun search(
        query: String,
        limit: Int = 40,
        resolve: Boolean? = null,
        following: Boolean? = null
    ): MastodonRequest<List<Account>> {
        val params = Parameter().apply {
            append("q", query)
            append("limit", limit)
            resolve?.let { append("resolve", it) }
            following?.let { append("following", it) }
        }
        return client.createListGetRequest<Account>("/api/v1/accounts/search", params)
    }

    /**
     * おすすめアカウントを取得
     * GET /api/v2/suggestions
     */
    fun getSuggestions(): MastodonRequest<List<Suggestion>> {
        return client.createListGetRequest<Suggestion>("/api/v2/suggestions")
    }

    /**
     * 指定したアカウントが含まれるリストを取得
     * GET /api/v1/accounts/:id/lists
     */
    fun getListsContainingThisAccount(accountId: String): MastodonRequest<Pageable<MstList>> {
        return client.createListGetRequest<MstList>("/api/v1/accounts/$accountId/lists").toPageable()
    }
}

/**
 * プラットフォーム固有のファイルアップロード関連拡張関数
 */
expect fun AccountsMethod.updateAvatar(avatarFile: Any, mimeType: String): MastodonRequest<CredentialAccount>
expect fun AccountsMethod.updateHeader(headerFile: Any, mimeType: String): MastodonRequest<CredentialAccount>