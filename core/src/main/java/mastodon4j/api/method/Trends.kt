package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.entity.Tag
import mastodon4j.api.exception.Mastodon4jRequestException

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#timelines
 */
class Trends(private val client: MastodonClient) {

    // GET /api/v1/trends/tags
    @JvmOverloads
    @Throws(Mastodon4jRequestException::class)
    fun getTrendsTags(limit: Int? = null, offset: Int? = null): MastodonRequest<List<Tag>> {
        return MastodonRequest<List<Tag>>(
            {
                client.get(
                    "/api/v1/trends/tags",
                    Parameter().apply {
                        limit?.let {
                            append("limit", it)
                        }
                        offset?.let {
                            append("offset", it)
                        }
                    }
                )
            },
            {
                client.getSerializer().fromJson(it, Tag::class.java)
            }
        )
    }
}
