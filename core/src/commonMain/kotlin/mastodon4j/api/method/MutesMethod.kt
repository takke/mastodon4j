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
        return client.createListGetRequest<Account>(
            path = "/api/v1/mutes",
            parameters = range?.toParameter()
        ).toPageable()
    }
}