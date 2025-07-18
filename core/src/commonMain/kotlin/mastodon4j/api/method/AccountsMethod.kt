package mastodon4j.api.method

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
        return client.createGetRequest<Account>("/api/v1/accounts/lookup?${params.build()}")
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
    fun updateCredentials(
        displayName: String? = null,
        note: String? = null,
        locked: Boolean? = null,
        bot: Boolean? = null,
        discoverable: Boolean? = null
    ): MastodonRequest<CredentialAccount> {
        val params = Parameter().apply {
            displayName?.let { append("display_name", it) }
            note?.let { append("note", it) }
            locked?.let { append("locked", it) }
            bot?.let { append("bot", it) }
            discoverable?.let { append("discoverable", it) }
        }
        return client.createPatchRequest<CredentialAccount>("/api/v1/accounts/update_credentials", params)
    }

    /**
     * アカウントのフォロワーリストを取得
     * GET /api/v1/accounts/:id/followers
     */
    fun getFollowers(accountId: String, range: Range? = null): MastodonRequest<Pageable<Account>> {
        val path = if (range != null) {
            "/api/v1/accounts/$accountId/followers?${range.toParameter().build()}"
        } else {
            "/api/v1/accounts/$accountId/followers"
        }
        return client.createGetRequest<Pageable<Account>>(path).toPageable()
    }

    /**
     * アカウントのフォローリストを取得
     * GET /api/v1/accounts/:id/following
     */
    fun getFollowing(accountId: String, range: Range? = null): MastodonRequest<Pageable<Account>> {
        val path = if (range != null) {
            "/api/v1/accounts/$accountId/following?${range.toParameter().build()}"
        } else {
            "/api/v1/accounts/$accountId/following"
        }
        return client.createGetRequest<Pageable<Account>>(path).toPageable()
    }

    /**
     * アカウントのステータスリストを取得
     * GET /api/v1/accounts/:id/statuses
     */
    fun getStatuses(
        accountId: String,
        onlyMedia: Boolean? = null,
        excludeReplies: Boolean? = null,
        pinned: Boolean? = null,
        range: Range? = null
    ): MastodonRequest<Pageable<Status>> {
        val params = Parameter().apply {
            onlyMedia?.let { append("only_media", it) }
            excludeReplies?.let { append("exclude_replies", it) }
            pinned?.let { append("pinned", it) }
            range?.let { 
                append("max_id", it.maxId ?: 0L)
                append("since_id", it.sinceId ?: "")
                append("limit", it.limit)
            }
        }
        
        val path = "/api/v1/accounts/$accountId/statuses?${params.build()}"
        return client.createGetRequest<Pageable<Status>>(path).toPageable()
    }

    /**
     * アカウントをフォロー
     * POST /api/v1/accounts/:id/follow
     */
    fun postFollow(accountId: String, reblogs: Boolean? = null): MastodonRequest<Relationship> {
        val params = Parameter().apply {
            reblogs?.let { append("reblogs", it) }
        }
        return client.createPostRequest<Relationship>("/api/v1/accounts/$accountId/follow", params)
    }

    /**
     * アカウントのフォローを解除
     * POST /api/v1/accounts/:id/unfollow
     */
    fun postUnfollow(accountId: String): MastodonRequest<Relationship> {
        return client.createPostRequest<Relationship>("/api/v1/accounts/$accountId/unfollow")
    }

    /**
     * アカウントをブロック
     * POST /api/v1/accounts/:id/block
     */
    fun postBlock(accountId: String): MastodonRequest<Relationship> {
        return client.createPostRequest<Relationship>("/api/v1/accounts/$accountId/block")
    }

    /**
     * アカウントのブロックを解除
     * POST /api/v1/accounts/:id/unblock
     */
    fun postUnblock(accountId: String): MastodonRequest<Relationship> {
        return client.createPostRequest<Relationship>("/api/v1/accounts/$accountId/unblock")
    }

    /**
     * アカウントをミュート
     * POST /api/v1/accounts/:id/mute
     */
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
    fun postUnmute(accountId: String): MastodonRequest<Relationship> {
        return client.createPostRequest<Relationship>("/api/v1/accounts/$accountId/unmute")
    }

    /**
     * 複数アカウントとの関係を取得
     * GET /api/v1/accounts/relationships
     */
    fun getRelationships(accountIds: List<String>): MastodonRequest<List<Relationship>> {
        val params = Parameter().append("id", accountIds)
        return client.createGetRequest<List<Relationship>>("/api/v1/accounts/relationships?${params.build()}")
    }

    /**
     * アカウントを検索
     * GET /api/v1/accounts/search
     */
    fun getAccountsSearch(
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
        return client.createGetRequest<List<Account>>("/api/v1/accounts/search?${params.build()}")
    }
}