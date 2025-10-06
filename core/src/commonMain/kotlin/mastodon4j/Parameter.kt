package mastodon4j

import io.ktor.http.*

/**
 * APIパラメータを構築するクラス
 */
class Parameter {
    private val parameters = mutableListOf<Pair<String, String>>()

    fun append(key: String, value: String): Parameter {
        parameters.add(Pair(key, value))
        return this
    }

    fun append(key: String, value: Int): Parameter = append(key, value.toString())

    fun append(key: String, value: Long): Parameter = append(key, value.toString())

    fun append(key: String, value: Boolean): Parameter = append(key, value.toString())

    fun <T> append(key: String, value: List<T>): Parameter {
        value.forEach {
            append("$key[]", it.toString())
        }
        return this
    }

    fun build(): String =
        parameters.joinToString(separator = "&") { "${it.first}=${it.second.encodeURLParameter()}" }

    fun toMap(): Map<String, List<String>> {
        return parameters.groupBy({ it.first }, { it.second })
    }

    /**
     * Ktorのパラメータ形式に変換
     */
    fun toParameters(): Parameters = parameters {
        this@Parameter.parameters.forEach { (key, value) ->
            append(key, value)
        }
    }
}