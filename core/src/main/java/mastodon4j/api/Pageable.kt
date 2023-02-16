package mastodon4j.api

class Pageable<T>(val part: List<T>, val link: mastodon4j.api.Link?) : MastodonResponse() {

    fun nextRange(limit: Int = 20): Range = Range(link?.maxId, limit = limit)
    fun prevRange(limit: Int = 20): Range = Range(sinceId = link?.sinceId, limit = limit)
    fun toRange(limit: Int = 20): Range = Range(link?.maxId, link?.sinceId, limit)
}
