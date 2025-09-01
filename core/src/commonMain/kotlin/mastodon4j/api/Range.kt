package mastodon4j.api

import mastodon4j.Parameter

class Range
constructor(val maxId: Long? = null, val sinceId: String? = null, val limit: Int = 20, val minId: String? = null) {
    fun toParameter() = Parameter().apply {
        maxId?.let { append("max_id", it) }
        sinceId?.let { append("since_id", it) }
        append("limit", limit)
        minId?.let { append("min_id", it) }
    }
}