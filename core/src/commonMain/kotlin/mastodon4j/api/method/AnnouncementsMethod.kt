package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.entity.Announcement
import mastodon4j.api.exception.MastodonException

/**
 * お知らせに関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://docs.joinmastodon.org/methods/announcements/
 */
class AnnouncementsMethod(private val client: MastodonClient) {

    /**
     * お知らせリストを取得
     * GET /api/v1/announcements
     */
    fun getAnnouncements(withDismissed: Boolean? = null): MastodonRequest<List<Announcement>> {
        val params = Parameter().apply {
            withDismissed?.let { append("with_dismissed", it) }
        }
        
        val path = if (params.build().isNotEmpty()) {
            "/api/v1/announcements?${params.build()}"
        } else {
            "/api/v1/announcements"
        }
        
        return client.createListGetRequest<Announcement>(path)
    }

    /**
     * お知らせを非表示にする
     * POST /api/v1/announcements/:id/dismiss
     */
    suspend fun dismissAnnouncement(announcementId: Long) {
        val response = client.post("/api/v1/announcements/$announcementId/dismiss")
        if (response.status.value !in 200..299) {
            throw MastodonException("Failed to dismiss announcement: ${response.status}")
        }
    }

    /**
     * お知らせにリアクションを追加
     * PUT /api/v1/announcements/:id/reactions/:name
     */
    suspend fun putAnnouncementReaction(announcementId: Long, emojiName: String) {
        val response = client.put("/api/v1/announcements/$announcementId/reactions/$emojiName")
        if (response.status.value !in 200..299) {
            throw MastodonException("Failed to add reaction to announcement: ${response.status}")
        }
    }

    /**
     * お知らせのリアクションを削除
     * DELETE /api/v1/announcements/:id/reactions/:name
     */
    suspend fun deleteAnnouncementReaction(announcementId: Long, emojiName: String) {
        val response = client.delete("/api/v1/announcements/$announcementId/reactions/$emojiName")
        if (response.status.value !in 200..299) {
            throw MastodonException("Failed to delete reaction from announcement: ${response.status}")
        }
    }
}