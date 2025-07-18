package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Status

/**
 * お気に入りに関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#favourites
 */
class FavouritesMethod(private val client: MastodonClient) {

    /**
     * お気に入りした投稿一覧を取得
     * GET /api/v1/favourites
     * 
     * @param range ページング用のレンジパラメータ
     */
    fun getFavourites(range: Range? = null): MastodonRequest<Pageable<Status>> {
        val path = if (range != null) {
            "/api/v1/favourites?${range.toParameter().build()}"
        } else {
            "/api/v1/favourites"
        }
        return client.createGetRequest<Pageable<Status>>(path).toPageable()
    }
}