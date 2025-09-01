package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://docs.joinmastodon.org/entities/StatusSource/
 */
@Serializable
data class StatusSource(
    @SerialName("id") val id: String = "",
    @SerialName("text") val text: String = "",
    @SerialName("spoiler_text") val spoilerText: String = "",
)