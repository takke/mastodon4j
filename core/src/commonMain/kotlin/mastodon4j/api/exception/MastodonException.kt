package mastodon4j.api.exception

import io.ktor.client.statement.*
import io.ktor.http.*
import mastodon4j.api.MastodonResponseImpl
import mastodon4j.api.RateLimit

class MastodonException : Exception {

    val requestUrl: String?
    val protocol: String?
    val headers: Map<String, String>?
    val rateLimit: RateLimit?

    // Http Status
    val code: Int

    // Http Status Message
    val statusMessage: String?

    // Http Response Body
    val responseBody: String?

    constructor(response: HttpResponse, errorMessage: String? = null) : super(errorMessage ?: response.status.description) {
        this.requestUrl = response.request.url.toString()
        this.protocol = response.version.name
        this.headers = response.headers.entries()
            .associate { it.key to it.value.firstOrNull().orEmpty() }
        this.rateLimit = MastodonResponseImpl.collectRateLimit(headers)
        this.code = response.status.value
        this.statusMessage = response.status.description
        this.responseBody = errorMessage
    }

    constructor(cause: Throwable) : super(cause) {
        this.requestUrl = null
        this.protocol = null
        this.headers = null
        this.rateLimit = null
        this.code = 0
        this.statusMessage = null
        this.responseBody = null
    }

    constructor(message: String) : super(message) {
        this.requestUrl = null
        this.protocol = null
        this.headers = null
        this.rateLimit = null
        this.code = 0
        this.statusMessage = null
        this.responseBody = null
    }

    fun isErrorResponse() = responseBody != null

    fun resourceNotFound(): Boolean {
        return code == 404
    }

    override fun toString(): String {
        val responseBody = responseBody ?: ""
        return "${responseBody}\n" + additionalErrorInfo
    }

    val additionalErrorInfo: String 
        get() = "code=$code, url=$requestUrl, rateLimit=$rateLimit protocol=$protocol, message=$statusMessage"
}