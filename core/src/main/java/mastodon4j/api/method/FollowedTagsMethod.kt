package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Range
import mastodon4j.api.entity.Tag

/**
 * See more https://docs.joinmastodon.org/methods/followed_tags/
 */
class FollowedTagsMethod(private val client: MastodonClient) {

    /**
     * GET /api/v1/followed_tags
     */
    fun getFollowedTags(range: Range = Range()): MastodonRequest<List<Tag>> {
        return MastodonRequest(
            {
                client.get(
                    "/api/v1/followed_tags",
                    range.toParameter()
                )
            },
            {
                client.getSerializer().fromJson(it, Tag::class.java)
            }
        )
    }
}
