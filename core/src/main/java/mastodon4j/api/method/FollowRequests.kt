package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Account
import mastodon4j.api.exception.Mastodon4jRequestException
import mastodon4j.extension.emptyRequestBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#follow-requests
 */
class FollowRequests(private val client: MastodonClient) {
    // GET /api/v1/follow_requests
    @JvmOverloads
    fun getFollowRequests(range: Range = Range()): MastodonRequest<Pageable<Account>> {
        return MastodonRequest<Pageable<Account>>(
            { client.get("follow_requests", range.toParameter()) },
            {
                client.getSerializer().fromJson(it, Account::class.java)
            }
        ).toPageable()
    }

    //  POST /api/v1/follow_requests/:id/authorize
    @Throws(Mastodon4jRequestException::class)
    fun postAuthorize(accountId: Long) {
        val response = client.post("follow_requests/$accountId/authorize", emptyRequestBody())
        if (!response.isSuccessful) {
            throw Mastodon4jRequestException(response)
        }
    }

    //  POST /api/v1/follow_requests/:id/reject
    @Throws(Mastodon4jRequestException::class)
    fun postReject(accountId: Long) {
        val response = client.post("follow_requests/$accountId/reject", emptyRequestBody())
        if (!response.isSuccessful) {
            throw Mastodon4jRequestException(response)
        }
    }
}