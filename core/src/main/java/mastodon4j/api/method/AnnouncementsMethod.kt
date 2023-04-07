package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.entity.Announcement
import mastodon4j.api.exception.MastodonException
import mastodon4j.extension.emptyRequestBody

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

    // PUT /api/v1/announcements/:id/reactions/:name
    @Throws(MastodonException::class)
    fun putAnnouncementReaction(announcementId: Long, emojiName: String) {
        val response = client.put("/api/v1/announcements/$announcementId/reactions/$emojiName", emptyRequestBody())
        if (!response.isSuccessful) {
            throw MastodonException(response)
        }
    }

    // DELETE /api/v1/announcements/:id/reactions/:name
    fun deleteAnnouncementReaction(announcementId: Long, emojiName: String) {
        val response = client.delete("/api/v1/announcements/$announcementId/reactions/$emojiName")
        if (!response.isSuccessful) {
            throw MastodonException(response)
        }
    }
}
