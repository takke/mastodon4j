package mastodon4j

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import mastodon4j.api.exception.MastodonException
// import mastodon4j.api.method.*

/**
 * Mastodon APIクライアント
 * 
 * Ktor Clientを使用してMastodon APIと通信するメインクラス
 */
class MastodonClient private constructor(
    private val instanceName: String,
    private val client: HttpClient,
    private val json: Json
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

    fun getSerializer() = json

    fun getInstanceName() = instanceName

    fun getHttpClient() = client

    // メソッドの作成
    // TODO: 各メソッドクラスをKMP対応後に有効化
    /*
    @Suppress("FunctionName")
    fun AnnouncementsMethod() = AnnouncementsMethod(this)

    @Suppress("FunctionName")
    fun Streaming() = Streaming(this)

    @Suppress("FunctionName")
    fun PublicMethod() = PublicMethod(this)

    @Suppress("FunctionName")
    fun AppsMethod() = AppsMethod(this)

    @Suppress("FunctionName")
    fun StatusesMethod() = StatusesMethod(this)

    @Suppress("FunctionName")
    fun AccountsMethod() = AccountsMethod(this)

    @Suppress("FunctionName")
    fun FollowsMethod() = FollowsMethod(this)

    @Suppress("FunctionName")
    fun TagsMethod() = TagsMethod(this)

    @Suppress("FunctionName")
    fun TrendsMethod() = TrendsMethod(this)

    @Suppress("FunctionName")
    fun FollowedTagsMethod() = FollowedTagsMethod(this)

    @Suppress("FunctionName")
    fun MediaMethod() = MediaMethod(this)

    @Suppress("FunctionName")
    fun MutesMethod() = MutesMethod(this)

    @Suppress("FunctionName")
    fun BlocksMethod() = BlocksMethod(this)

    @Suppress("FunctionName")
    fun FavouritesMethod() = FavouritesMethod(this)

    @Suppress("FunctionName")
    fun BookmarksMethod() = BookmarksMethod(this)

    @Suppress("FunctionName")
    fun ListsMethod() = ListsMethod(this)

    @Suppress("FunctionName")
    fun FollowRequestsMethod() = FollowRequestsMethod(this)

    @Suppress("FunctionName")
    fun TimelinesMethod() = TimelinesMethod(this)

    @Suppress("FunctionName")
    fun NotificationsMethod() = NotificationsMethod(this)

    @Suppress("FunctionName")
    fun SearchMethod() = SearchMethod(this)

    @Suppress("FunctionName")
    fun ReportsMethod() = ReportsMethod(this)
    */
}