package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Conversation
import mastodon4j.api.entity.Status
import mastodon4j.api.exception.MastodonException

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#timelines
 */
class TimelinesMethod(private val client: MastodonClient) {

    // GET /api/v1/timelines/home
    @JvmOverloads
    @Throws(MastodonException::class)
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

    // GET /api/v1/conversations
    @JvmOverloads
    @Throws(MastodonException::class)
    fun getConversations(range: Range = Range()): MastodonRequest<Pageable<Conversation>> {
        return MastodonRequest<Pageable<Conversation>>(
            {
                client.get("/api/v1/conversations", range.toParameter())
            },
            {
                client.getSerializer().fromJson(it, Conversation::class.java)
            }
        ).toPageable()
    }
}
