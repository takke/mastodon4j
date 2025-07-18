package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Range
import mastodon4j.api.entity.*
import mastodon4j.api.exception.MastodonException

/**
 * See more https://docs.joinmastodon.org/methods/search/
 */
class SearchMethod(private val client: MastodonClient) {

    //  GET /api/v2/search
    @Throws(MastodonException::class)
    fun getSearch2(query: String, resolve: Boolean = false, range: Range? = null, type: String? = null): MastodonRequest<Results> {

        return MastodonRequest(
            {
                client.get("/api/v2/search", (range?.toParameter() ?: Parameter()).apply {
                    append("q", query)
                    if (resolve) {
                        append("resolve", true)
                    }
                    if (type != null) {
                        append("type", type)
                    }
                })
            },
            {
                client.getSerializer().fromJson(it, Results::class.java)
            }
        )
    }

    /**
     * GET /api/v1/search
     * q: The search query
     * resolve: Whether to resolve non-local accounts
     * @see "https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#search"
     */
    @JvmOverloads
    fun getSearch(query: String, resolve: Boolean = false): MastodonRequest<ResultsV1> {
        return MastodonRequest(
            {
                client.get(
                    "/api/v1/search",
                    Parameter().apply {
                        append("q", query)
                        if (resolve) {
                            append("resolve", resolve)
                        }
                    }
                )
            },
            {
                client.getSerializer().fromJson(it, ResultsV1::class.java)
            }
        )
    }

}
