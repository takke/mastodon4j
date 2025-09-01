package mastodon4j.api.method

import androidx.annotation.CheckResult
import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Account

/**
 * フォローリクエストに関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#follow-requests
 */
class FollowRequestsMethod(private val client: MastodonClient) {
    
    /**
     * フォローリクエスト一覧を取得
     * GET /api/v1/follow_requests
     * 
     * @param range ページング用のレンジパラメータ
     */
    fun getFollowRequests(range: Range? = null): MastodonRequest<Pageable<Account>> {
        return client.createListGetRequest<Account>(
            path = "/api/v1/follow_requests",
            parameters = range?.toParameter()
        ).toPageable()
    }

    /**
     * フォローリクエストを承認
     * POST /api/v1/follow_requests/:id/authorize
     * 
     * @param accountId 承認するアカウントのID
     */
    @CheckResult
    fun postAuthorize(accountId: String): MastodonRequest<Unit> {
        return client.createPostRequest<Unit>("/api/v1/follow_requests/$accountId/authorize")
    }

    /**
     * フォローリクエストを拒否
     * POST /api/v1/follow_requests/:id/reject
     * 
     * @param accountId 拒否するアカウントのID
     */
    @CheckResult
    fun postReject(accountId: String): MastodonRequest<Unit> {
        return client.createPostRequest<Unit>("/api/v1/follow_requests/$accountId/reject")
    }
}