package mastodon4j

import com.google.gson.Gson
import mastodon4j.api.exception.MastodonException
import mastodon4j.api.method.AccountsMethod
import mastodon4j.api.method.AnnouncementsMethod
import mastodon4j.api.method.AppsMethod
import mastodon4j.api.method.BlocksMethod
import mastodon4j.api.method.BookmarksMethod
import mastodon4j.api.method.FavouritesMethod
import mastodon4j.api.method.FollowRequestsMethod
import mastodon4j.api.method.FollowsMethod
import mastodon4j.api.method.ListsMethod
import mastodon4j.api.method.MediaMethod
import mastodon4j.api.method.MutesMethod
import mastodon4j.api.method.NotificationsMethod
import mastodon4j.api.method.PublicMethod
import mastodon4j.api.method.ReportsMethod
import mastodon4j.api.method.SearchMethod
import mastodon4j.api.method.StatusesMethod
import mastodon4j.api.method.StreamingMethod
import mastodon4j.api.method.TimelinesMethod
import mastodon4j.api.method.TrendsMethod
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


open class MastodonClient
private constructor(
    private val instanceName: String,
    private val client: OkHttpClient,
    private val gson: Gson
) {

    class Builder(
        private val instanceName: String,
        private val okHttpClientBuilder: OkHttpClient.Builder,
        private val gson: Gson
    ) {

        private var accessToken: String? = null
        private var debug = false

        fun accessToken(accessToken: String) = apply {
            this.accessToken = accessToken
        }

        fun useStreamingApi() = apply {
            okHttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        }

        fun debug() = apply {
            this.debug = true
        }

        fun build(): MastodonClient {
            return MastodonClient(
                instanceName,
                okHttpClientBuilder.addNetworkInterceptor(AuthorizationInterceptor(accessToken)).build(),
                gson
            ).also {
                it.debug = debug
            }
        }
    }

    private var debug = false

    fun debugPrint(log: String) {
        if (debug) {
            println(log)
        }
    }

    private class AuthorizationInterceptor(val accessToken: String? = null) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val compressedRequest = originalRequest.newBuilder()
                .headers(originalRequest.headers)
                .method(originalRequest.method, originalRequest.body)
                .apply {
                    accessToken?.let {
                        header("Authorization", String.format("Bearer %s", it))
                    }
                }
                .build()
            return chain.proceed(compressedRequest)
        }
    }

    val baseUrl = "https://${instanceName}"

    open fun getSerializer() = gson

    open fun getInstanceName() = instanceName

    open fun get(path: String, parameter: Parameter? = null): Response {
        try {
            val url = "$baseUrl$path"
            debugPrint(url)
            val urlWithParams = parameter?.let {
                "$url?${it.build()}"
            } ?: url
            val call = client.newCall(
                Request.Builder()
                    .url(urlWithParams)
                    .get()
                    .build()
            )
            return call.execute()
        } catch (e: IOException) {
            throw MastodonException(e)
        }
    }

    open fun post(path: String, body: RequestBody): Response {
        try {
            val url = "$baseUrl$path"
            debugPrint(url)
            val call = client.newCall(
                Request.Builder()
                    .url(url)
                    .post(body)
                    .build()
            )
            return call.execute()
        } catch (e: IllegalArgumentException) {
            throw MastodonException(e)
        } catch (e: IOException) {
            throw MastodonException(e)
        }
    }

    open fun put(path: String, body: RequestBody): Response {
        try {
            val url = "$baseUrl$path"
            debugPrint(url)
            val call = client.newCall(
                Request.Builder()
                    .url(url)
                    .put(body)
                    .build()
            )
            return call.execute()
        } catch (e: IllegalArgumentException) {
            throw MastodonException(e)
        } catch (e: IOException) {
            throw MastodonException(e)
        }
    }

    open fun patch(path: String, body: RequestBody): Response {
        try {
            val url = "$baseUrl$path"
            debugPrint(url)
            val call = client.newCall(
                Request.Builder()
                    .url(url)
                    .patch(body)
                    .build()
            )
            return call.execute()
        } catch (e: IOException) {
            throw MastodonException(e)
        }
    }

    open fun delete(path: String, body: RequestBody? = null): Response {
        try {
            val url = "$baseUrl$path"
            debugPrint(url)
            val call = client.newCall(
                Request.Builder()
                    .url(url)
                    .delete(body)
                    .build()
            )
            return call.execute()
        } catch (e: IOException) {
            throw MastodonException(e)
        }
    }

    //--------------------------------------------------
    // Methods
    //--------------------------------------------------

    val accounts get() = AccountsMethod(this)
    val apps get() = AppsMethod(this)
    val blocks get() = BlocksMethod(this)
    val bookmarks get() = BookmarksMethod(this)
    val favourites get() = FavouritesMethod(this)
    val followRequests get() = FollowRequestsMethod(this)
    val follows get() = FollowsMethod(this)
    val lists get() = ListsMethod(this)
    val media get() = MediaMethod(this)
    val mutes get() = MutesMethod(this)
    val notifications get() = NotificationsMethod(this)
    val public get() = PublicMethod(this)
    val reports get() = ReportsMethod(this)
    val search get() = SearchMethod(this)
    val statuses get() = StatusesMethod(this)
    val streaming get() = StreamingMethod(this)
    val timelines get() = TimelinesMethod(this)
    val trends get() = TrendsMethod(this)
    val announcements get() = AnnouncementsMethod(this)

}
