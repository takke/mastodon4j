package mastodon4j

import com.google.gson.JsonParser
import mastodon4j.api.exception.Mastodon4jRequestException
import mastodon4j.extension.toPageable
import okhttp3.Response

open class MastodonRequest<T>(
    private val executor: () -> Response,
    private val mapper: (String) -> Any
) {
    interface Action1<T> {
        fun invoke(arg1: T, arg2: Any)
    }

    private var action: (json: String, value: Any) -> Unit = { _, _ -> }

    private var isPageable: Boolean = false

    internal fun toPageable() = apply {
        isPageable = true
    }

    @JvmSynthetic
    fun doOnJson(action: (json: String, value: Any) -> Unit) = apply {
        this.action = action
    }

    fun doOnJson(action: Action1<String>) = apply {
        this.action = { json, value -> action.invoke(json, value) }
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(Mastodon4jRequestException::class)
    fun execute(): T {
        val response = executor()
        if (response.isSuccessful) {
            try {
                val body = response.body?.string() ?: throw Mastodon4jRequestException(response)
                val element = JsonParser().parse(body)
                if (element.isJsonObject) {
                    val v = mapper(body)
                    action(body, v)
                    return v as T
                } else {
                    val list = arrayListOf<Any>()
                    element.asJsonArray.forEach {
                        val json = it.toString()
                        val v = mapper(json)
                        action(json, v)
                        list.add(v)
                    }
                    return if (isPageable) {
                        list.toPageable(response) as T
                    } else {
                        list as T
                    }
                }
            } catch (e: Exception) {
                throw Mastodon4jRequestException(e)
            }
        } else {
            throw Mastodon4jRequestException(response)
        }
    }
}