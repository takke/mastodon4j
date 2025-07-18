package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Range
import mastodon4j.api.entity.Tag

/**
 * フォロー中のタグに関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://docs.joinmastodon.org/methods/followed_tags/
 */
class FollowedTagsMethod(private val client: MastodonClient) {

    /**
     * フォロー中のタグ一覧を取得
     * GET /api/v1/followed_tags
     * 
     * @param range ページング用のレンジパラメータ
     */
    fun getFollowedTags(range: Range? = null): MastodonRequest<List<Tag>> {
        val path = if (range != null) {
            "/api/v1/followed_tags?${range.toParameter().build()}"
        } else {
            "/api/v1/followed_tags"
        }
        return client.createListGetRequest<Tag>(path)
    }
}