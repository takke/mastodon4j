package mastodon4j.api

interface MastodonResponse<T> {

    val value: T

    val code: Int
    val description: String?

    val headers: Map<String, String>

    val rateLimit: RateLimit?

    suspend fun responseBody(): String?
}
