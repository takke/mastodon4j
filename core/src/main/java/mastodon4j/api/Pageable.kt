package mastodon4j.api

class Pageable<T>(val part: List<T>, val link: Link?) {

    fun nextRange(limit: Int = 20): Range = Range(link?.maxId, limit = limit)
    fun prevRange(limit: Int = 20): Range = Range(sinceId = link?.sinceId.toString(), limit = limit)
    fun toRange(limit: Int = 20): Range = Range(link?.maxId, link?.sinceId.toString(), limit)
}
