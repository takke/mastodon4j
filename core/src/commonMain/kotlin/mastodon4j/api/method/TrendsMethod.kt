package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Pageable
import mastodon4j.api.entity.Status
import mastodon4j.api.entity.Tag

/**
 * トレンドに関するAPIメソッドクラス（KMP対応版）
 * 
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#timelines
 */
class TrendsMethod(private val client: MastodonClient) {

    /**
     * トレンドタグを取得
     * GET /api/v1/trends/tags
     * 
     * @param limit 取得する最大数（オプション）
     * @param offset 取得開始位置のオフセット（オプション）
     */
    fun getTrendsTags(limit: Int? = null, offset: Int? = null): MastodonRequest<List<Tag>> {
        val params = if (limit != null || offset != null) {
            Parameter().apply {
                limit?.let { append("limit", it) }
                offset?.let { append("offset", it) }
            }
        } else {
            null
        }
        
        return client.createListGetRequest<Tag>("/api/v1/trends/tags", params)
    }

    /**
     * トレンド投稿(話題の投稿)を取得（v3.5.0以降）
     * GET /api/v1/trends/statuses
     *
     * スコア順に返却される。ページングは max_id ではなく offset で行う。
     *
     * @param limit 取得する最大数（オプション、既定20、最大40）
     * @param offset 取得開始位置のオフセット（オプション）
     */
    fun getTrendsStatuses(limit: Int? = null, offset: Int? = null): MastodonRequest<Pageable<Status>> {
        val params = if (limit != null || offset != null) {
            Parameter().apply {
                limit?.let { append("limit", it) }
                offset?.let { append("offset", it) }
            }
        } else {
            null
        }

        return client.createListGetRequest<Status>("/api/v1/trends/statuses", params).toPageable()
    }

    /**
     * トレンドを取得（v3.5.0以降では getTrendsTags() を使用することを推奨）
     * GET /api/v1/trends
     * 
     * @param limit 取得する最大数（オプション）
     * @param offset 取得開始位置のオフセット（オプション）
     */
    fun getTrends(limit: Int? = null, offset: Int? = null): MastodonRequest<List<Tag>> {
        val params = if (limit != null || offset != null) {
            Parameter().apply {
                limit?.let { append("limit", it) }
                offset?.let { append("offset", it) }
            }
        } else {
            null
        }
        
        return client.createListGetRequest<Tag>("/api/v1/trends", params)
    }
}