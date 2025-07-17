package mastodon4j

import java.net.URLEncoder

class LegacyParameter {
    private val parameters = ArrayList<Pair<String, String>>()

    fun append(key: String, value: String): LegacyParameter {
        parameters.add(Pair(key, value))
        return this
    }

    fun append(key: String, value: Int): LegacyParameter = append(key, value.toString())

    fun append(key: String, value: Long): LegacyParameter = append(key, value.toString())

    fun append(key: String, value: Boolean): LegacyParameter = append(key, value.toString())

    fun <T> append(key: String, value: List<T>): LegacyParameter {
        value.forEach {
            append("$key[]", it.toString())
        }
        return this
    }

    fun build(): String =
        parameters
            .map {
                "${it.first}=${URLEncoder.encode(it.second, "utf-8")}"
            }
            .joinToString(separator = "&")

}
