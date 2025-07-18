package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Account

/**
 * ブロックに関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#blocks
 */
class BlocksMethod(private val client: MastodonClient) {

    /**
     * ブロックしたアカウント一覧を取得
     * GET /api/v1/blocks
     * 
     * @param range ページング用のレンジパラメータ
     */
    fun getBlocks(range: Range? = null): MastodonRequest<Pageable<Account>> {
        val path = if (range != null) {
            "/api/v1/blocks?${range.toParameter().build()}"
        } else {
            "/api/v1/blocks"
        }
        return client.createListGetRequest<Account>(path).toPageable()
    }
}