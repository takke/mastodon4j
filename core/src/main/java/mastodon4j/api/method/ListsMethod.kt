package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.MastodonList
import mastodon4j.api.entity.Status
import mastodon4j.api.exception.Mastodon4jRequestException

class ListsMethod(private val client: MastodonClient) {

    // GET /api/v1/lists
    fun getLists(): MastodonRequest<Pageable<MastodonList>> {
        return MastodonRequest<Pageable<MastodonList>>(
            {
                client.get("/api/v1/lists")
            },
            {
                client.getSerializer().fromJson(it, MastodonList::class.java)
            }
        ).toPageable()
    }

    //GET /api/v1/timelines/list/:list_id
    @Throws(Mastodon4jRequestException::class)
    fun getListTimeLine(listID: Long, range: Range = Range()): MastodonRequest<Pageable<Status>> {
        return MastodonRequest<Pageable<Status>>(
            {
                client.get("/api/v1/timelines/list/$listID", range.toParameter())
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        ).toPageable()
    }
}