package com.sys1yagi.mastodon4j.api.method

import com.sys1yagi.mastodon4j.testtool.MockClient
import mastodon4j.api.exception.MastodonException
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBe
import org.junit.Assert.*
import org.junit.Test

class NotificationsTest {
    @Test
    fun getNotifications() {
        val client = MockClient.mock("notifications.json")
        val notifications = Notifications(client)
        val pageable = notifications.getNotifications().execute()
        val notification = pageable.part.first()
        notification.type shouldEqualTo "favourite"
        notification.account shouldNotBe null
        notification.status shouldNotBe null
    }

    @Test(expected = MastodonException::class)
    fun getNotificationsWithException() {
        val client = MockClient.ioException()
        val notifications = Notifications(client)
        notifications.getNotifications().execute()
    }

    @Test(expected = MastodonException::class)
    fun getNotificationWithException() {
        val client = MockClient.ioException()
        val notifications = Notifications(client)
        notifications.getNotification(1L).execute()
    }
}
