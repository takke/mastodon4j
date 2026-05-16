package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 予約投稿（スケジュール投稿）エンティティ
 *
 * see more https://docs.joinmastodon.org/entities/ScheduledStatus/
 */
@Serializable
data class ScheduledStatus(
    @SerialName("id") val id: String = "",
    @SerialName("scheduled_at") val scheduledAt: String = "",
    @SerialName("params") val params: Params? = null,
    @SerialName("media_attachments") val mediaAttachments: List<MediaAttachment> = emptyList(),
) {
    /**
     * 予約投稿時に保持される投稿パラメータ
     */
    @Serializable
    data class Params(
        @SerialName("text") val text: String = "",
        @SerialName("media_ids") val mediaIds: List<String>? = null,
        @SerialName("sensitive") val sensitive: Boolean? = null,
        @SerialName("spoiler_text") val spoilerText: String? = null,
        @SerialName("visibility") val visibility: String? = null,
        @SerialName("in_reply_to_id") val inReplyToId: String? = null,
        @SerialName("language") val language: String? = null,
        @SerialName("application_id") val applicationId: Long? = null,
        @SerialName("idempotency") val idempotency: String? = null,
        @SerialName("with_rate_limit") val withRateLimit: Boolean = false,
        @SerialName("scheduled_at") val scheduledAt: String? = null,
    )
}
