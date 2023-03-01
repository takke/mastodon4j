package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.Parameter
import mastodon4j.api.Dispatcher
import mastodon4j.api.Handler
import mastodon4j.api.Shutdownable
import mastodon4j.api.entity.Notification
import mastodon4j.api.entity.Status
import mastodon4j.api.exception.MastodonException

class StreamingMethod(private val client: MastodonClient) {
    @Throws(MastodonException::class)
    fun federatedPublic(handler: Handler): Shutdownable {
        val response = client.get("/api/v1/streaming/public")
        if (response.isSuccessful) {
            val body = response.body ?: throw MastodonException(response)
            val reader = body.byteStream().bufferedReader()
            val dispatcher = Dispatcher()
            dispatcher.invokeLater(Runnable {
                while (true) {
                    try {
                        val line = reader.readLine()
                        if (line == null || line.isEmpty()) {
                            continue
                        }
                        val type = line.split(":")[0].trim()
                        if (type != "event") {
                            continue
                        }
                        val event = line.split(":")[1].trim()
                        val payload = reader.readLine()
                        val payloadType = payload.split(":")[0].trim()
                        if (payloadType != "data") {
                            continue
                        }
                        if (event == "update") {
                            val start = payload.indexOf(":") + 1
                            val json = payload.substring(start).trim()
                            val status = client.getSerializer().fromJson(
                                json,
                                Status::class.java
                            )
                            handler.onStatus(status)
                        }
                    } catch (e: java.io.InterruptedIOException) {
                        break
                    }
                }
                reader.close()
            })
            return Shutdownable(dispatcher)
        } else {
            throw MastodonException(response)
        }
    }

    @Throws(MastodonException::class)
    fun localPublic(handler: Handler): Shutdownable {
        val response = client.get("/api/v1/streaming/public/local")
        if (response.isSuccessful) {
            val body = response.body ?: throw MastodonException(response)
            val reader = body.byteStream().bufferedReader()
            val dispatcher = Dispatcher()
            dispatcher.invokeLater(Runnable {
                while (true) {
                    try {
                        val line = reader.readLine()
                        if (line == null || line.isEmpty()) {
                            continue
                        }
                        val type = line.split(":")[0].trim()
                        if (type != "event") {
                            continue
                        }
                        val event = line.split(":")[1].trim()
                        val payload = reader.readLine()
                        val payloadType = payload.split(":")[0].trim()
                        if (payloadType != "data") {
                            continue
                        }
                        if (event == "update") {
                            val start = payload.indexOf(":") + 1
                            val json = payload.substring(start).trim()
                            val status = client.getSerializer().fromJson(
                                json,
                                Status::class.java
                            )
                            handler.onStatus(status)
                        }
                    } catch (e: java.io.InterruptedIOException) {
                        break
                    }
                }
                reader.close()
            })
            return Shutdownable(dispatcher)
        } else {
            throw MastodonException(response)
        }
    }

    @Throws(MastodonException::class)
    fun federatedHashtag(tag: String, handler: Handler): Shutdownable {
        val response = client.get("/api/v1/streaming/hashtag", Parameter().append("tag", tag))
        if (response.isSuccessful) {
            val body = response.body ?: throw MastodonException(response)
            val reader = body.byteStream().bufferedReader()
            val dispatcher = Dispatcher()
            dispatcher.invokeLater(Runnable {
                while (true) {
                    try {
                        val line = reader.readLine()
                        if (line == null || line.isEmpty()) {
                            continue
                        }
                        val type = line.split(":")[0].trim()
                        if (type != "event") {
                            continue
                        }
                        val event = line.split(":")[1].trim()
                        val payload = reader.readLine()
                        val payloadType = payload.split(":")[0].trim()
                        if (payloadType != "data") {
                            continue
                        }
                        if (event == "update") {
                            val start = payload.indexOf(":") + 1
                            val json = payload.substring(start).trim()
                            val status = client.getSerializer().fromJson(
                                json,
                                Status::class.java
                            )
                            handler.onStatus(status)
                        }
                    } catch (e: java.io.InterruptedIOException) {
                        break
                    }
                }
                reader.close()
            })
            return Shutdownable(dispatcher)
        } else {
            throw MastodonException(response)
        }
    }

    @Throws(MastodonException::class)
    fun localHashtag(tag: String, handler: Handler): Shutdownable {
        val response = client.get("/api/v1/streaming/hashtag/local", Parameter().append("tag", tag))
        if (response.isSuccessful) {
            val body = response.body ?: throw MastodonException(response)
            val reader = body.byteStream().bufferedReader()
            val dispatcher = Dispatcher()
            dispatcher.invokeLater(Runnable {
                while (true) {
                    try {
                        val line = reader.readLine()
                        if (line == null || line.isEmpty()) {
                            continue
                        }
                        val type = line.split(":")[0].trim()
                        if (type != "event") {
                            continue
                        }
                        val event = line.split(":")[1].trim()
                        val payload = reader.readLine()
                        val payloadType = payload.split(":")[0].trim()
                        if (payloadType != "data") {
                            continue
                        }
                        if (event == "update") {
                            val start = payload.indexOf(":") + 1
                            val json = payload.substring(start).trim()
                            val status = client.getSerializer().fromJson(
                                json,
                                Status::class.java
                            )
                            handler.onStatus(status)
                        }
                    } catch (e: java.io.InterruptedIOException) {
                        break
                    }
                }
                reader.close()
            })
            return Shutdownable(dispatcher)
        } else {
            throw MastodonException(response)
        }
    }

    @Throws(MastodonException::class)
    fun user(handler: Handler): Shutdownable {
        val response = client.get("/api/v1/streaming/user")
        if (response.isSuccessful) {
            val body = response.body ?: throw MastodonException(response)
            val reader = body.byteStream().bufferedReader()
            val dispatcher = Dispatcher()
            dispatcher.invokeLater(Runnable {
                while (true) {
                    try {
                        val line = reader.readLine()
                        if (line == null || line.isEmpty()) {
                            continue
                        }
                        val type = line.split(":")[0].trim()
                        if (type != "event") {
                            continue
                        }
                        val event = line.split(":")[1].trim()
                        val payload = reader.readLine()
                        val payloadType = payload.split(":")[0].trim()
                        if (payloadType != "data") {
                            continue
                        }

                        val start = payload.indexOf(":") + 1
                        val json = payload.substring(start).trim()
                        if (event == "update") {
                            val status = client.getSerializer().fromJson(
                                json,
                                Status::class.java
                            )
                            handler.onStatus(status)
                        }
                        if (event == "notification") {
                            val notification = client.getSerializer().fromJson(
                                json,
                                Notification::class.java
                            )
                            handler.onNotification(notification)
                        }
                        if (event == "delete") {
                            val id = client.getSerializer().fromJson(
                                json,
                                Long::class.java
                            )
                            handler.onDelete(id)
                        }
                    } catch (e: java.io.InterruptedIOException) {
                        break
                    }
                }
                reader.close()
            })
            return Shutdownable(dispatcher)
        } else {
            throw MastodonException(response)
        }
    }

    @Throws(MastodonException::class)
    fun userList(handler: Handler, listID: String): Shutdownable {
        val response = client.get("/api/v1/streaming/list", Parameter().apply {
            append("list", listID)
        }
        )
        if (response.isSuccessful) {
            val body = response.body ?: throw MastodonException(response)
            val reader = body.byteStream().bufferedReader()
            val dispatcher = Dispatcher()
            dispatcher.invokeLater(Runnable {
                while (true) {
                    try {
                        val line = reader.readLine()
                        if (line == null || line.isEmpty()) {
                            continue
                        }
                        val type = line.split(":")[0].trim()
                        if (type != "event") {
                            continue
                        }
                        val event = line.split(":")[1].trim()
                        val payload = reader.readLine()
                        val payloadType = payload.split(":")[0].trim()
                        if (payloadType != "data") {
                            continue
                        }

                        val start = payload.indexOf(":") + 1
                        val json = payload.substring(start).trim()
                        if (event == "update") {
                            val status = client.getSerializer().fromJson(
                                json,
                                Status::class.java
                            )
                            handler.onStatus(status)
                        }
                        if (event == "notification") {
                            val notification = client.getSerializer().fromJson(
                                json,
                                Notification::class.java
                            )
                            handler.onNotification(notification)
                        }
                        if (event == "delete") {
                            val id = client.getSerializer().fromJson(
                                json,
                                Long::class.java
                            )
                            handler.onDelete(id)
                        }
                    } catch (e: java.io.InterruptedIOException) {
                        break
                    }
                }
                reader.close()
            })
            return Shutdownable(dispatcher)
        } else {
            throw MastodonException(response)
        }
    }
}
