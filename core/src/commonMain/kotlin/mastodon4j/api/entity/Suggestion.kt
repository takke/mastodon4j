package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Suggestion(
    @SerialName("source") val source: String = "",  // staff, past_interactions, global
    @SerialName("account") val account: Account? = null,
)
