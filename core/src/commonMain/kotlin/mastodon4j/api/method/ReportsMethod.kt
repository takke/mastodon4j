package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Report

/**
 * レポート（通報）に関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#reports
 */
class ReportsMethod(private val client: MastodonClient) {
    
    /**
     * 提出済みレポート一覧を取得
     * GET /api/v1/reports
     * 
     * @param range ページング用のレンジパラメータ
     */
    fun getReports(range: Range? = null): MastodonRequest<Pageable<Report>> {
        val path = if (range != null) {
            "/api/v1/reports?${range.toParameter().build()}"
        } else {
            "/api/v1/reports"
        }
        return client.createGetRequest<List<Report>>(path).toPageable()
    }

    /**
     * 新しいレポートを提出
     * POST /api/v1/reports
     * 
     * @param accountId 通報対象のアカウントID
     * @param statusId 通報対象の投稿ID
     * @param comment 通報に関するコメント
     */
    fun postReport(
        accountId: Long,
        statusId: Long,
        comment: String
    ): MastodonRequest<Report> {
        val params = Parameter().apply {
            append("account_id", accountId)
            append("status_ids", statusId)
            append("comment", comment)
        }
        return client.createPostRequest<Report>("/api/v1/reports", params)
    }
}