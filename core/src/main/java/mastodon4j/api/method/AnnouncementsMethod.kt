package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.entity.Announcement

/**
 * See more https://docs.joinmastodon.org/methods/announcements/
 */
class AnnouncementsMethod(private val client: MastodonClient) {

    // GET /api/v1/announcements
    fun getAnnouncements(withDismissed: Boolean? = null): MastodonRequest<List<Announcement>> {
        return MastodonRequest(
            {
                client.get(
                    "/api/v1/announcements",
                    Parameter().apply {
                        withDismissed?.let {
                            append("with_dismissed", it)
                        }
                    }
                )
            },
            {
                client.getSerializer().fromJson(it, Announcement::class.java)
            }
        )
    }
}
