package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Poll(

    @SerialName("id") val id: String = "",
    @SerialName("expires_at") val expiresAt: String? = "",
    @SerialName("expired") val expired: Boolean = false,
    @SerialName("multiple") val multiple: Boolean = false,
    @SerialName("votes_count") val votesCount: Long = 0,
    @SerialName("voters_count") val votersCount: Long? = null,
    @SerialName("voted") val voted: Boolean = false,
    @SerialName("own_votes") val ownVotes: List<Int> = emptyList(),
    @SerialName("options") val options: List<PollOption> = emptyList(),
    @SerialName("emojis") val emojis: List<Emoji> = emptyList(),
)