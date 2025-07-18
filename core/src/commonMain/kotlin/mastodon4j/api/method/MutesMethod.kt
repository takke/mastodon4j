package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Account

/**
 * ミュートに関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#mutes
 */
class MutesMethod(private val client: MastodonClient) {
    
    /**
     * ミュートしたアカウント一覧を取得
     * GET /api/v1/mutes
     * 
     * @param range ページング用のレンジパラメータ
     */
    fun getMutes(range: Range? = null): MastodonRequest<Pageable<Account>> {
        val path = if (range != null) {
            "/api/v1/mutes?${range.toParameter().build()}"
        } else {
            "/api/v1/mutes"
        }
        return client.createGetRequest<List<Account>>(path).toPageable()
    }
}