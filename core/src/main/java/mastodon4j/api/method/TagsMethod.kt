package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.entity.Tag
import mastodon4j.extension.emptyRequestBody

/**
 * See more https://docs.joinmastodon.org/methods/tags
 */
class TagsMethod(private val client: MastodonClient) {

    // POST /api/v1/tags/:id/follow
    fun followTag(id: String): MastodonRequest<Tag> {

        return MastodonRequest(
            {
                client.post(
                    "/api/v1/tags/$id/follow", emptyRequestBody()
                )
            },
            {
                client.getSerializer().fromJson(it, Tag::class.java)
            }
        )
    }

    // POST /api/v1/tags/:id/unfollow
    fun unfollowTag(id: String): MastodonRequest<Tag> {

        return MastodonRequest(
            {
                client.post(
                    "/api/v1/tags/$id/unfollow", emptyRequestBody()
                )
            },
            {
                client.getSerializer().fromJson(it, Tag::class.java)
            }
        )
    }

    // GET /api/v1/tags/:id
    fun getTag(id: String): MastodonRequest<Tag> {

        return MastodonRequest(
            {
                client.get("/api/v1/tags/$id")
            },
            {
                client.getSerializer().fromJson(it, Tag::class.java)
            }
        )
    }

}
