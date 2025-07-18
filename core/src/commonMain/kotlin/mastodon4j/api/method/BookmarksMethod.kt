package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Status

/**
 * ブックマークに関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://docs.joinmastodon.org/methods/bookmarks/
 */
class BookmarksMethod(private val client: MastodonClient) {

    /**
     * ブックマークした投稿一覧を取得
     * GET /api/v1/bookmarks
     * 
     * @param range ページング用のレンジパラメータ
     */
    fun getBookmarks(range: Range? = null): MastodonRequest<Pageable<Status>> {
        val path = if (range != null) {
            "/api/v1/bookmarks?${range.toParameter().build()}"
        } else {
            "/api/v1/bookmarks"
        }
        return client.createGetRequest<List<Status>>(path).toPageable()
    }
}