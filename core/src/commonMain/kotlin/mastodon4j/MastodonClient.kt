package mastodon4j

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import mastodon4j.api.method.*
import mastodon4j.compat.GsonCompatLayer

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
            serializer = { jsonString -> json.decodeFromString<T>(jsonString) as Any }
        )
    }
    
    /**
     * List型用のGETリクエストを作成（要素型指定）
     */
    inline fun <reified E> createListGetRequest(path: String): MastodonRequest<List<E>> {
        return MastodonRequest(
            executor = { get(path) },
            serializer = { jsonString -> json.decodeFromString<List<E>>(jsonString) as Any },
            elementSerializer = { elementJson -> json.decodeFromString<E>(elementJson) as Any }
        )
    }

    /**
     * 型安全なPOSTリクエストを作成
     */
    inline fun <reified T> createPostRequest(path: String, parameters: Parameter? = null): MastodonRequest<T> {
        return MastodonRequest(
            executor = { post(path, parameters) },
            serializer = { jsonString -> json.decodeFromString<T>(jsonString) as Any }
        )
    }

    /**
     * 型安全なPUTリクエストを作成
     */
    inline fun <reified T> createPutRequest(path: String, parameters: Parameter? = null): MastodonRequest<T> {
        return MastodonRequest(
            executor = { put(path, parameters) },
            serializer = { jsonString -> json.decodeFromString<T>(jsonString) as Any }
        )
    }

    /**
     * 型安全なDELETEリクエストを作成
     */
    inline fun <reified T> createDeleteRequest(path: String): MastodonRequest<T> {
        return MastodonRequest(
            executor = { delete(path) },
            serializer = { jsonString -> json.decodeFromString<T>(jsonString) as Any }
        )
    }

    /**
     * 型安全なPATCHリクエストを作成
     */
    inline fun <reified T> createPatchRequest(path: String, parameters: Parameter? = null): MastodonRequest<T> {
        return MastodonRequest(
            executor = { patch(path, parameters) },
            serializer = { jsonString -> json.decodeFromString<T>(jsonString) as Any }
        )
    }

    // メソッド
    val accounts = AccountsMethod(this)
    val apps = AppsMethod(this)
    val announcements get() = AnnouncementsMethod(this)
    val blocks get() = BlocksMethod(this)
    val bookmarks get() = BookmarksMethod(this)
    val favourites get() = FavouritesMethod(this)
    val followRequests get() = FollowRequestsMethod(this)
    val followedTags get() = FollowedTagsMethod(this)
    val follows get() = FollowsMethod(this)
    val lists get() = ListsMethod(this)
    val media get() = MediaMethod(this)
    val mutes get() = MutesMethod(this)
    val notifications get() = NotificationsMethod(this)
    val public get() = PublicMethod(this)
    val reports get() = ReportsMethod(this)
    val search get() = SearchMethod(this)
    val statuses get() = StatusesMethod(this)
    val tags get() = TagsMethod(this)
    val timelines get() = TimelinesMethod(this)
    val trends get() = TrendsMethod(this)

    // TODO: Streamingの実装（WebSocket対応）
//   val streaming get() = Streaming(this)
}