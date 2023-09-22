package mastodon4j.api

interface MastodonResponse<T> {

    val result: T

    val code: Int

    val headers: Map<String, String>

    val rateLimit: RateLimit?
}
