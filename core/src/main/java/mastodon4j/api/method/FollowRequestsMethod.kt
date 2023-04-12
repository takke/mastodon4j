package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Account
import mastodon4j.api.exception.MastodonException
import mastodon4j.extension.emptyRequestBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#follow-requests
 */
class FollowRequestsMethod(private val client: MastodonClient) {
    // GET /api/v1/follow_requests
    @JvmOverloads
    fun getFollowRequests(range: Range = Range()): MastodonRequest<Pageable<Account>> {
        return MastodonRequest<Pageable<Account>>(
            { client.get("/api/v1/follow_requests", range.toParameter()) },
            {
                client.getSerializer().fromJson(it, Account::class.java)
            }
        ).toPageable()
    }

    //  POST /api/v1/follow_requests/:id/authorize
    @Throws(MastodonException::class)
    fun postAuthorize(accountId: String) {
        val response = client.post("/api/v1/follow_requests/$accountId/authorize", emptyRequestBody())
        if (!response.isSuccessful) {
            throw MastodonException(response)
        }
    }

    //  POST /api/v1/follow_requests/:id/reject
    @Throws(MastodonException::class)
    fun postReject(accountId: String) {
        val response = client.post("/api/v1/follow_requests/$accountId/reject", emptyRequestBody())
        if (!response.isSuccessful) {
            throw MastodonException(response)
        }
    }
}
