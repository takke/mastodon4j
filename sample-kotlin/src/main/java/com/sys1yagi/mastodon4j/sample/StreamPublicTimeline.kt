package com.sys1yagi.mastodon4j.sample

import mastodon4j.api.Handler
import mastodon4j.api.entity.Notification
import mastodon4j.api.entity.Status
import mastodon4j.api.exception.MastodonException
import mastodon4j.api.method.Streaming


object StreamPublicTimeline {

    @JvmStatic fun main(args: Array<String>) {
        val instanceName = args[0]
        val credentialFilePath = args[1]

        // require authentication even if public streaming
        val client = Authenticator.appRegistrationIfNeeded(instanceName, credentialFilePath, true)
        val handler = object : Handler {
            override fun onStatus(status: Status) {
                println(status.content)
            }

            override fun onNotification(notification: Notification) {

            }

            override fun onDelete(id: Long) {

            }

            override fun onDisconnected(retryable: Retryable) {

            }
        }
        val streaming = Streaming(client)
        try {
            val shutdownable = streaming.localPublic(handler)
            Thread.sleep(10000L)
            shutdownable.shutdown()
        } catch(e: MastodonException) {
            println("error")
            println(e.response?.code())
            println(e.response?.message())
            println(e.response?.body()?.string())
            return
        }
    }
}
