package mastodon4j.api

import mastodon4j.api.entity.Notification
import mastodon4j.api.entity.Status


interface Handler {

    fun onStatus(status: Status)

    //ignore if public streaming
    fun onNotification(notification: Notification)

    //ignore if public streaming
    fun onDelete(id: Long)

    fun onDisconnected(retryable: Retryable)
}