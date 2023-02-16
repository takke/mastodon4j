package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Status
import mastodon4j.api.exception.Mastodon4jRequestException

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#timelines
 */
class Timelines(private val client: MastodonClient) {
    //  GET /api/v1/timelines/home
    @JvmOverloads
    @Throws(Mastodon4jRequestException::class)
    fun getHome(range: Range = Range()): MastodonRequest<Pageable<Status>> {
        return MastodonRequest<Pageable<Status>>(
            {
                client.get("/api/v1/timelines/home", range.toParameter())
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        ).toPageable()
    }
}
