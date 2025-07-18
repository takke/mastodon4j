package mastodon4j

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import mastodon4j.api.exception.MastodonException
import mastodon4j.compat.GsonCompatLayer
// import mastodon4j.api.method.*

/**
 * Mastodon APIクライアント
 * 
 * Ktor Clientを使用してMastodon APIと通信するメインクラス
 */
class MastodonClient private constructor(
    private val instanceName: String,
    private val client: HttpClient,
    val json: Json
) {
    /**
     * MastodonClientビルダー
     */
    class Builder(
        private val instanceName: String,
        private val httpClientConfig: HttpClientConfig<*>.() -> Unit = {},
        private val json: Json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    ) {
        private var accessToken: String? = null
        private var debug = false

        fun accessToken(accessToken: String) = apply {
            this.accessToken = accessToken
        }

        fun useStreamingApi() = apply {
            // Ktorでのタイムアウト設定は HttpClientConfig で行う
        }

        fun debug() = apply {
            this.debug = true
        }

        fun build(): MastodonClient {
            val client = HttpClient {
                // ユーザーが指定した設定を適用
                httpClientConfig()

                // 共通設定
                install(ContentNegotiation) {
                    json(json)
                }

                // アクセストークンが設定されている場合はヘッダーに追加
                accessToken?.let { token ->
                    defaultRequest {
                        headers {
                            append("Authorization", "Bearer $token")
                        }
                    }
                }

                // デバッグモードの場合はロギングを有効化
                // TODO: ロギングプラグインを追加

                // タイムアウト設定
                install(HttpTimeout) {
                    requestTimeoutMillis = 60_000
                    connectTimeoutMillis = 60_000
                    socketTimeoutMillis = 60_000
                }

                // デフォルトリクエスト設定
                defaultRequest {
                    url {
                        protocol = io.ktor.http.URLProtocol.HTTPS
                        host = instanceName
                    }
                }
            }

            return MastodonClient(instanceName, client, json).also {
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

    val baseUrl = "https://$instanceName"

    fun getSerializer() = GsonCompatLayer(json)

    fun getInstanceName() = instanceName

    fun getHttpClient() = client

    /**
     * GETリクエストを作成
     */
    suspend fun get(path: String): HttpResponse {
        return client.get(path)
    }

    /**
     * POSTリクエストを作成
     */
    suspend fun post(path: String, parameters: Parameter? = null): HttpResponse {
        return client.post(path) {
            parameters?.let {
                setBody(it.toParameters())
            }
        }
    }

    /**
     * PUTリクエストを作成
     */
    suspend fun put(path: String, parameters: Parameter? = null): HttpResponse {
        return client.put(path) {
            parameters?.let {
                setBody(it.toParameters())
            }
        }
    }

    /**
     * DELETEリクエストを作成
     */
    suspend fun delete(path: String): HttpResponse {
        return client.delete(path)
    }

    /**
     * PATCHリクエストを作成
     */
    suspend fun patch(path: String, parameters: Parameter? = null): HttpResponse {
        return client.patch(path) {
            parameters?.let {
                setBody(it.toParameters())
            }
        }
    }

    /**
     * 型安全なGETリクエストを作成
     */
    inline fun <reified T> createGetRequest(path: String): MastodonRequest<T> {
        return MastodonRequest(
            executor = { get(path) },
            serializer = { jsonString -> json.decodeFromString<T>(jsonString) }
        )
    }

    /**
     * 型安全なPOSTリクエストを作成
     */
    inline fun <reified T> createPostRequest(path: String, parameters: Parameter? = null): MastodonRequest<T> {
        return MastodonRequest(
            executor = { post(path, parameters) },
            serializer = { jsonString -> json.decodeFromString<T>(jsonString) }
        )
    }

    /**
     * 型安全なPUTリクエストを作成
     */
    inline fun <reified T> createPutRequest(path: String, parameters: Parameter? = null): MastodonRequest<T> {
        return MastodonRequest(
            executor = { put(path, parameters) },
            serializer = { jsonString -> json.decodeFromString<T>(jsonString) }
        )
    }

    /**
     * 型安全なDELETEリクエストを作成
     */
    inline fun <reified T> createDeleteRequest(path: String): MastodonRequest<T> {
        return MastodonRequest(
            executor = { delete(path) },
            serializer = { jsonString -> json.decodeFromString<T>(jsonString) }
        )
    }

    /**
     * 型安全なPATCHリクエストを作成
     */
    inline fun <reified T> createPatchRequest(path: String, parameters: Parameter? = null): MastodonRequest<T> {
        return MastodonRequest(
            executor = { patch(path, parameters) },
            serializer = { jsonString -> json.decodeFromString<T>(jsonString) }
        )
    }

    // メソッドの作成（KMP対応版）
    @Suppress("FunctionName")
    fun StatusesMethod() = mastodon4j.api.method.StatusesMethod(this)

    @Suppress("FunctionName")
    fun AccountsMethod() = mastodon4j.api.method.AccountsMethod(this)

    @Suppress("FunctionName")
    fun TimelinesMethod() = mastodon4j.api.method.TimelinesMethod(this)

    @Suppress("FunctionName")
    fun NotificationsMethod() = mastodon4j.api.method.NotificationsMethod(this)

    @Suppress("FunctionName")
    fun SearchMethod() = mastodon4j.api.method.SearchMethod(this)

    @Suppress("FunctionName")
    fun PublicMethod() = mastodon4j.api.method.PublicMethod(this)

    @Suppress("FunctionName")
    fun AppsMethod() = mastodon4j.api.method.AppsMethod(this)

    @Suppress("FunctionName")
    fun FollowsMethod() = mastodon4j.api.method.FollowsMethod(this)

    @Suppress("FunctionName")
    fun TagsMethod() = mastodon4j.api.method.TagsMethod(this)

    @Suppress("FunctionName")
    fun TrendsMethod() = mastodon4j.api.method.TrendsMethod(this)

    @Suppress("FunctionName")
    fun FollowedTagsMethod() = mastodon4j.api.method.FollowedTagsMethod(this)

    @Suppress("FunctionName")
    fun MediaMethod() = mastodon4j.api.method.MediaMethod(this)

    @Suppress("FunctionName")
    fun MutesMethod() = mastodon4j.api.method.MutesMethod(this)

    @Suppress("FunctionName")
    fun BlocksMethod() = mastodon4j.api.method.BlocksMethod(this)

    @Suppress("FunctionName")
    fun FavouritesMethod() = mastodon4j.api.method.FavouritesMethod(this)

    @Suppress("FunctionName")
    fun BookmarksMethod() = mastodon4j.api.method.BookmarksMethod(this)

    @Suppress("FunctionName")
    fun ListsMethod() = mastodon4j.api.method.ListsMethod(this)

    @Suppress("FunctionName")
    fun FollowRequestsMethod() = mastodon4j.api.method.FollowRequestsMethod(this)

    @Suppress("FunctionName")
    fun ReportsMethod() = mastodon4j.api.method.ReportsMethod(this)

    @Suppress("FunctionName")
    fun AnnouncementsMethod() = mastodon4j.api.method.AnnouncementsMethod(this)

    // TODO: Streamingの実装（WebSocket対応）
    /*
    @Suppress("FunctionName")
    fun Streaming() = Streaming(this)
    */
}