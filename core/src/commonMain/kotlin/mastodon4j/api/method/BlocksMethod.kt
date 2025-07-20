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
        return client.createListGetRequest<Account>("/api/v1/blocks", range?.toParameter()).toPageable()
    }
}