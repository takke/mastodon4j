package mastodon4j.api.method

import androidx.annotation.CheckResult
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
        return client.createListGetRequest<Report>("/api/v1/reports", range?.toParameter()).toPageable()
    }

    /**
     * 新しいレポートを提出
     * POST /api/v1/reports
     *
     * See https://docs.joinmastodon.org/methods/reports/#post
     *
     * @param accountId 通報対象のアカウントID
     * @param statusIds 証拠として添付する投稿IDのリスト(status_ids[] として送信)
     * @param comment 通報理由(サーバー既定の上限は1000文字)
     * @param forward リモートアカウントの場合、相手サーバーの管理者にも転送するか (v2.3.0以降)
     * @param category 通報カテゴリ (v3.5.0以降。未指定時はサーバー側で other 扱い)
     * @param ruleIds 違反したサーバールールのIDリスト(category=violation 時のみ。rule_ids[] として送信)
     */
    @CheckResult
    fun postReport(
        accountId: String,
        statusIds: List<String>? = null,
        comment: String? = null,
        forward: Boolean? = null,
        category: Report.Category? = null,
        ruleIds: List<String>? = null,
    ): MastodonRequest<Report> {
        val params = Parameter().apply {
            append("account_id", accountId)
            statusIds?.let { append("status_ids", it) }
            comment?.let { append("comment", it) }
            forward?.let { append("forward", it) }
            category?.let { append("category", it.value) }
            ruleIds?.let { append("rule_ids", it) }
        }
        return client.createPostRequest<Report>("/api/v1/reports", params)
    }
}