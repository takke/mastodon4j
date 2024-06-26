package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Report
import mastodon4j.api.exception.MastodonException
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#reports
 */
class ReportsMethod(private val client: MastodonClient) {
    // GET /api/v1/reports
    @JvmOverloads
    @Throws(MastodonException::class)
    fun getReports(range: Range = Range()): MastodonRequest<Pageable<Report>> {
        return MastodonRequest<Pageable<Report>>(
            {
                client.get(
                    "/api/v1/reports",
                    range.toParameter()
                )
            },
            {
                client.getSerializer().fromJson(it, Report::class.java)
            }
        ).toPageable()
    }

    /**
     * POST /api/v1/reports
     * account_id: The ID of the account to report
     * status_ids: The IDs of statuses to report (can be an array)
     * comment: A comment to associate with the report.
     */
    @Throws(MastodonException::class)
    fun postReport(accountId: Long, statusId: Long, comment: String): MastodonRequest<Report> {
        val parameters = Parameter().apply {
            append("account_id", accountId)
            append("status_ids", statusId)
            append("comment", comment)
        }.build()
        return MastodonRequest<Report>(
            {
                client.post(
                    "/api/v1/reports",
                    parameters
                        .toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull())
                )
            },
            {
                client.getSerializer().fromJson(it, Report::class.java)
            }
        )
    }
}
