package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Account
import mastodon4j.api.entity.MstList
import mastodon4j.api.entity.Status
import mastodon4j.api.exception.MastodonException

class ListsMethod(private val client: MastodonClient) {

    // GET /api/v1/lists
    fun getLists(): MastodonRequest<Pageable<MstList>> {
        return MastodonRequest<Pageable<MstList>>(
            {
                client.get("/api/v1/lists")
            },
            {
                client.getSerializer().fromJson(it, MstList::class.java)
            }
        ).toPageable()
    }

    // GET /api/v1/timelines/list/:list_id
    @Throws(MastodonException::class)
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

    // GET /api/v1/lists/:id/accounts
    @JvmOverloads
    fun getListAccounts(listId: Long, range: Range = Range()): MastodonRequest<Pageable<Account>> {
        return MastodonRequest<Pageable<Account>>(
            {
                client.get(
                    "/api/v1/lists/$listId/accounts",
                    range.toParameter()
                )
            },
            {
                client.getSerializer().fromJson(it, Account::class.java)
            }
        ).toPageable()
    }
}