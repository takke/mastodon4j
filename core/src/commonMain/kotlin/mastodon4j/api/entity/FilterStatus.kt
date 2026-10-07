package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * フィルター対象として登録された投稿
 *
 * see https://docs.joinmastodon.org/entities/FilterStatus/
 */
@Serializable
data class FilterStatus(
    @SerialName("id") val id: String = "",
    @SerialName("status_id") val statusId: String = "",
)
