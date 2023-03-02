package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Account
import mastodon4j.api.entity.MstList
import mastodon4j.api.entity.MstListRepliesPolicy
import mastodon4j.api.entity.Status
import mastodon4j.api.exception.MastodonException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

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

    // GET /api/v1/lists/:id
    fun getList(listId: Long): MastodonRequest<MstList> {
        return MastodonRequest(
            {
                client.get("/api/v1/lists/$listId")
            },
            {
                client.getSerializer().fromJson(it, MstList::class.java)
            }
        )
    }

    // GET /api/v1/timelines/list/:list_id
    @Throws(MastodonException::class)
    fun getListTimeLine(listId: Long, range: Range = Range()): MastodonRequest<Pageable<Status>> {
        return MastodonRequest<Pageable<Status>>(
            {
                client.get("/api/v1/timelines/list/$listId", range.toParameter())
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

    /**
     * POST /api/v1/lists
     */
    @Throws(MastodonException::class)
    fun createList(
        title: String,
        repliesPolicy: MstListRepliesPolicy? = null,
    ): MastodonRequest<MstList> {
        val parameters = Parameter().apply {
            append("title", title)
            repliesPolicy?.let {
                append("replies_policy", it.value)
            }
        }.build()

        return MastodonRequest(
            {
                client.post(
                    "/api/v1/lists",
                    parameters
                        .toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull())
                )
            },
            {
                client.getSerializer().fromJson(it, MstList::class.java)
            }
        )
    }

    /**
     * PUT /api/v1/lists
     */
    @Throws(MastodonException::class)
    fun editList(
        listId: Long,
        title: String,
        repliesPolicy: MstListRepliesPolicy? = null,
    ): MastodonRequest<MstList> {
        val parameters = Parameter().apply {
            append("title", title)
            repliesPolicy?.let {
                append("replies_policy", it.value)
            }
        }.build()

        return MastodonRequest(
            {
                client.put(
                    "/api/v1/lists/$listId",
                    parameters
                        .toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull())
                )
            },
            {
                client.getSerializer().fromJson(it, MstList::class.java)
            }
        )
    }
}