package mastodon4j.api

import io.ktor.client.statement.*

class MastodonResponseImpl<T>(
    override val value: T,
) : MastodonResponse<T> {

    override var code: Int = -1
    override var headers: Map<String, String> = emptyMap()
    
    // 内部使用のプロパティ
    internal var response: HttpResponse? = null
    internal var rateLimitValue: Int? = null
    internal var rateLimitRemaining: Int? = null
    internal var rateLimitReset: Long? = null

    override val rateLimit: RateLimit?
        get() {
            return if (rateLimitValue != null && rateLimitRemaining != null && rateLimitReset != null) {
                RateLimit(
                    limit = rateLimitValue!!.toLong(),
                    remaining = rateLimitRemaining!!.toLong(),
                    reset = rateLimitReset.toString()
                )
            } else {
                collectRateLimit(headers)
            }
        }

    fun collectResponse(response: HttpResponse) {
        this.response = response
        this.code = response.status.value
        this.headers = response.headers.entries()
            .associate { it.key to it.value.firstOrNull().orEmpty() }
    }

    companion object {
        fun collectRateLimit(headers: Map<String, String>): RateLimit? {
            var limit: Long? = null
            var remaining: Long? = null
            var reset: String? = null

            for ((key, value) in headers) {
                when {
                    key.equals("X-RateLimit-Limit", true) -> {
                        limit = value.toLongOrNull()
                    }

                    key.equals("X-RateLimit-Remaining", true) -> {
                        remaining = value.toLongOrNull()
                    }

                    key.equals("X-RateLimit-Reset", true) -> {
                        // 2023-09-22T13:45:00.694552Z
                        reset = value
                    }
                }
            }

            return if (limit == null || remaining == null || reset == null) {
                null
            } else {
                RateLimit(limit, remaining, reset)
            }
        }
    }
}