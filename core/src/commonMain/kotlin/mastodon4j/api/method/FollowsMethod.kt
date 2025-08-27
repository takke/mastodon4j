package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.entity.Account
import org.slf4j.helpers.CheckReturnValue

/**
 * フォローに関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#follows
 */
class FollowsMethod(private val client: MastodonClient) {
    
    /**
     * リモートアカウントをフォロー
     * POST /api/v1/follows
     * 
     * @param uri フォローしたいアカウントのusername@domain形式の識別子
     */
    @CheckReturnValue
    fun postRemoteFollow(uri: String): MastodonRequest<Account> {
        val params = Parameter().append("uri", uri)
        return client.createPostRequest<Account>("/api/v1/follows", params)
    }
}