package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.entity.Relationship
import mastodon4j.api.entity.Tag
import mastodon4j.extension.emptyRequestBody

/**
 * See more https://docs.joinmastodon.org/methods/tags
 */
class TagsMethod(private val client: MastodonClient) {

    // POST /api/v1/tags/:id/follow
    fun followTag(id: String): MastodonRequest<Relationship> {

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

}
