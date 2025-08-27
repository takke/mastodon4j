package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.entity.Tag
import org.slf4j.helpers.CheckReturnValue

/**
 * タグに関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://docs.joinmastodon.org/methods/tags
 */
class TagsMethod(private val client: MastodonClient) {

    /**
     * タグをフォロー
     * POST /api/v1/tags/:id/follow
     * 
     * @param id フォローするタグのID
     */
    @CheckReturnValue
    fun followTag(id: String): MastodonRequest<Tag> {
        return client.createPostRequest<Tag>("/api/v1/tags/$id/follow")
    }

    /**
     * タグのフォローを解除
     * POST /api/v1/tags/:id/unfollow
     * 
     * @param id フォロー解除するタグのID
     */
    @CheckReturnValue
    fun unfollowTag(id: String): MastodonRequest<Tag> {
        return client.createPostRequest<Tag>("/api/v1/tags/$id/unfollow")
    }

    /**
     * タグの詳細情報を取得
     * GET /api/v1/tags/:id
     * 
     * @param id 取得するタグのID
     */
    fun getTag(id: String): MastodonRequest<Tag> {
        return client.createGetRequest<Tag>("/api/v1/tags/$id")
    }
}