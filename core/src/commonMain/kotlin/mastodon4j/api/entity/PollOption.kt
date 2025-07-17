package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PollOption(
    @SerialName("title") val title: String = "",
    @SerialName("votes_count") val votesCount: Long? = 0
)