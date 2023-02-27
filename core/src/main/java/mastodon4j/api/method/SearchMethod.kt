package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.entity.*
import mastodon4j.api.exception.Mastodon4jRequestException

/**
 * See more https://docs.joinmastodon.org/methods/search/
 */
class SearchMethod(private val client: MastodonClient) {

    //  GET /api/v2/search
    @Throws(Mastodon4jRequestException::class)
    fun getSearch2(query: String, resolve: Boolean = false): MastodonRequest<Results> {

        return MastodonRequest(
            {
                client.get("/api/v2/search", Parameter().apply {
                    append("q", query)
                    if (resolve) {
                        append("resolve", resolve)
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
