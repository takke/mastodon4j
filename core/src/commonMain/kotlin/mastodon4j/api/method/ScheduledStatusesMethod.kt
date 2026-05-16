package mastodon4j.api.method

import androidx.annotation.CheckResult
import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.ScheduledStatus
import mastodon4j.api.exception.MastodonException

/**
 * 予約投稿（スケジュール投稿）に関するAPIメソッドクラス
 *
 * see more https://docs.joinmastodon.org/methods/scheduled_statuses/
 */
class ScheduledStatusesMethod(private val client: MastodonClient) {

    /**
     * 予約投稿の一覧を取得
     * GET /api/v1/scheduled_statuses
     */
    @CheckResult
    fun getScheduledStatuses(range: Range? = null): MastodonRequest<Pageable<ScheduledStatus>> {
        return client.createListGetRequest<ScheduledStatus>(
            path = "/api/v1/scheduled_statuses",
            parameters = range?.toParameter()
        ).toPageable()
    }

    /**
     * 指定IDの予約投稿を取得
     * GET /api/v1/scheduled_statuses/:id
     */
    @CheckResult
    fun getScheduledStatus(id: String): MastodonRequest<ScheduledStatus> {
        return client.createGetRequest<ScheduledStatus>("/api/v1/scheduled_statuses/$id")
    }

    /**
     * 予約投稿の公開日時を変更
     * PUT /api/v1/scheduled_statuses/:id
     *
     * @param scheduledAt 新しい公開予約日時（ISO 8601形式）
     */
    @CheckResult
    fun updateScheduledStatus(id: String, scheduledAt: String): MastodonRequest<ScheduledStatus> {
        val params = Parameter().apply {
            append("scheduled_at", scheduledAt)
        }
        return client.createPutRequest<ScheduledStatus>("/api/v1/scheduled_statuses/$id", params)
    }

    /**
     * 予約投稿をキャンセル（削除）
     * DELETE /api/v1/scheduled_statuses/:id
     */
    suspend fun cancelScheduledStatus(id: String) {
        val response = client.delete("/api/v1/scheduled_statuses/$id")
        if (response.status.value !in 200..299) {
            throw MastodonException("Failed to cancel scheduled status: ${response.status}")
        }
    }
}
