package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Notification
import mastodon4j.api.exception.MastodonException
import mastodon4j.extension.emptyRequestBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#notifications
 */
class NotificationsMethod(private val client: MastodonClient) {
    // GET /api/v1/notifications
    @JvmOverloads
    fun getNotifications(
        range: Range = Range(),
        types: List<Notification.Type>? = null,
        excludeTypes: List<Notification.Type>? = null
    ): MastodonRequest<Pageable<Notification>> {
        val parameter = range.toParameter()
        if (excludeTypes != null) {
            parameter.append("exclude_types", excludeTypes.map { it.value })
        }
        if (types != null) {
            parameter.append("types", types.map { it.value })
        }
        return MastodonRequest<Pageable<Notification>>(
            {
                client.get("/api/v1/notifications", parameter)
            },
            {
                client.getSerializer().fromJson(it, Notification::class.java)
            }
        ).toPageable()
    }

    // GET /api/v1/notifications/:id
    fun getNotification(id: Long): MastodonRequest<Notification> {
        return MastodonRequest<Notification>(
            {
                client.get("/api/v1/notifications/$id")
            },
            {
                client.getSerializer().fromJson(it, Notification::class.java)
            }
        )
    }

    //  POST /api/v1/notifications/clear
    @Throws(MastodonException::class)
    fun clearNotifications() {
        val response = client.post("/api/v1/notifications/clear", emptyRequestBody())
        if (!response.isSuccessful) {
            throw MastodonException(response)
        }
    }
}
