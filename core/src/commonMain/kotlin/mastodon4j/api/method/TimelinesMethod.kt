package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Conversation
import mastodon4j.api.entity.Status
import mastodon4j.api.exception.MastodonException

/**
 * タイムラインに関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#timelines
 */
class TimelinesMethod(private val client: MastodonClient) {

    /**
     * ホームタイムラインを取得
     * GET /api/v1/timelines/home
     */
    fun getHome(range: Range? = null): MastodonRequest<Pageable<Status>> {
        val path = if (range != null) {
            "/api/v1/timelines/home?${range.toParameter().build()}"
        } else {
            "/api/v1/timelines/home"
        }
        return client.createListGetRequest<Status>(path).toPageable()
    }

    /**
     * 会話リストを取得
     * GET /api/v1/conversations
     */
    fun getConversations(range: Range? = null): MastodonRequest<Pageable<Conversation>> {
        val path = if (range != null) {
            "/api/v1/conversations?${range.toParameter().build()}"
        } else {
            "/api/v1/conversations"
        }
        return client.createListGetRequest<Conversation>(path).toPageable()
    }

    /**
     * パーソナルタイムラインを取得（fedibird専用）
     * GET /api/v1/timelines/personal
     */
    fun getPersonalTimelines(range: Range): MastodonRequest<Pageable<Status>> {
        val path = "/api/v1/timelines/personal?${range.toParameter().build()}"
        return client.createListGetRequest<Status>(path).toPageable()
    }
}