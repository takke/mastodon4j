package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Notification
import mastodon4j.api.exception.MastodonException

/**
 * 通知に関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#notifications
 */
class NotificationsMethod(private val client: MastodonClient) {

    /**
     * 通知リストを取得
     * GET /api/v1/notifications
     */
    fun getNotifications(
        range: Range? = null,
        types: List<Notification.Type>? = null,
        excludeTypes: List<Notification.Type>? = null
    ): MastodonRequest<Pageable<Notification>> {
        val params = range?.toParameter() ?: Parameter()
        
        excludeTypes?.let { 
            params.append("exclude_types", it.map { type -> type.value })
        }
        
        types?.let { 
            params.append("types", it.map { type -> type.value })
        }
        
        val path = "/api/v1/notifications?${params.build()}"
        return client.createGetRequest<Pageable<Notification>>(path).toPageable()
    }

    /**
     * 指定されたIDの通知を取得
     * GET /api/v1/notifications/:id
     */
    fun getNotification(id: Long): MastodonRequest<Notification> {
        return client.createGetRequest<Notification>("/api/v1/notifications/$id")
    }

    /**
     * 通知をクリア
     * POST /api/v1/notifications/clear
     */
    suspend fun clearNotifications() {
        val response = client.post("/api/v1/notifications/clear")
        if (response.status.value !in 200..299) {
            throw MastodonException("Failed to clear notifications: ${response.status}")
        }
    }

    /**
     * 指定した通知を既読にマーク
     * POST /api/v1/notifications/:id/dismiss
     */
    fun dismissNotification(id: Long): MastodonRequest<Unit> {
        return MastodonRequest(
            executor = { client.post("/api/v1/notifications/$id/dismiss") },
            serializer = { _ -> Unit }
        )
    }

    /**
     * 通知を削除
     * DELETE /api/v1/notifications/:id
     */
    fun deleteNotification(id: Long): MastodonRequest<Unit> {
        return MastodonRequest(
            executor = { client.delete("/api/v1/notifications/$id") },
            serializer = { _ -> Unit }
        )
    }
}