package mastodon4j

import io.ktor.client.call.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.*
import mastodon4j.api.MastodonResponse
import mastodon4j.api.MastodonResponseImpl
import mastodon4j.api.Pageable
import mastodon4j.api.exception.MastodonException
import mastodon4j.extension.toPageable

/**
 * Mastodon APIリクエストを表すクラス
 * 
 * @param T レスポンスの型
 * @param executor HTTPリクエストを実行する関数
 * @param serializer JSONをオブジェクトに変換するシリアライザー
 * @param elementSerializer 配列要素用のシリアライザー（オプション）
 */
class MastodonRequest<T>(
    private val executor: suspend () -> HttpResponse,
    private val serializer: suspend (String) -> Any,
    private val elementSerializer: (suspend (String) -> Any)? = null
) {
    interface Action1<T> {
        fun invoke(arg1: T, arg2: Any)
    }

    private var action: (json: String, value: Any) -> Unit = { _, _ -> }
    private var arrayAction: ((jsonArray: String) -> Unit)? = null

    private var isPageable: Boolean = false

    /**
     * List<T>のリクエストをPageable<T>のリクエストに変換
     */
    @Suppress("UNCHECKED_CAST")
    internal fun <T> toPageable(): MastodonRequest<Pageable<T>> = apply {
        isPageable = true
    } as MastodonRequest<Pageable<T>>

    @JvmSynthetic
    fun doOnJson(action: (json: String, value: Any) -> Unit) = apply {
        this.action = action
    }

    fun doOnJsonArray(arrayAction: (jsonArray: String) -> Unit) = apply {
        this.arrayAction = arrayAction
    }

    fun doOnJson(action: Action1<String>) = apply {
        this.action = { json, value -> action.invoke(json, value) }
    }

    /**
     * リクエストを実行してレスポンスを取得
     * 
     * @return MastodonResponse<T>
     * @throws MastodonException APIエラーが発生した場合
     */
    @Suppress("UNCHECKED_CAST")
    suspend fun executeAsync(): MastodonResponse<T> {
        val response = executor()
        
        if (response.status.isSuccess()) {
            try {
                val body = response.bodyAsText()
                val element = Json.parseToJsonElement(body)

                val result = when {
                    element is JsonObject -> {
                        if (isPageable) {
                            // Pageableリクエストでオブジェクトが返された場合はエラー
                            throw MastodonException("Expected JSON array for pageable request, but got object. This might be an API error or single item response.")
                        } else {
                            // 単一オブジェクトの場合
                            val v = serializer(body)
                            action(body, v)
                            v as T
                        }
                    }
                    element is JsonArray -> {
                        // 配列の場合
                        val list = mutableListOf<Any>()
                        element.forEach { jsonElement ->
                            val json = jsonElement.toString()
                            // 配列の場合は要素用のシリアライザーを使用、なければ通常のシリアライザー
                            val v = elementSerializer?.invoke(json) ?: serializer(json)
                            action(json, v)
                            list.add(v)
                        }

                        arrayAction?.invoke(body)

                        if (isPageable) {
                            list.toPageable(response) as T
                        } else {
                            list as T
                        }
                    }
                    else -> {
                        throw MastodonException("Unexpected JSON type: ${element::class.simpleName}")
                    }
                }

                return MastodonResponseImpl(result).also {
                    // レスポンスヘッダーなどの情報を収集
                    it.response = response
                    it.rateLimitValue = response.headers["X-RateLimit-Limit"]?.toIntOrNull()
                    it.rateLimitRemaining = response.headers["X-RateLimit-Remaining"]?.toIntOrNull()
                    it.rateLimitReset = response.headers["X-RateLimit-Reset"]?.toLongOrNull()
                }
            } catch (e: Exception) {
                when (e) {
                    is MastodonException -> throw e
                    else -> throw MastodonException(cause = e)
                }
            }
        } else {
            // エラーレスポンスの処理
            val errorBody = try {
                response.bodyAsText()
            } catch (e: Exception) {
                null
            }
            
            throw MastodonException(
                response = response,
                errorMessage = errorBody
            )
        }
    }

    /**
     * 同期でリクエストを実行
     */
    fun execute(): MastodonResponse<T> = runBlocking { executeAsync() }

    /**
     * Execute request and return value
     *
     * If you want to get response code, headers or RateLimit, use [execute]
     */
    suspend fun executeAsyncAndGetValue(): T {
        return executeAsync().value
    }

    fun executeAndGetValue(): T {
        return execute().value
    }
}