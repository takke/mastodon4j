package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Quote(
    // "pending", "accepted", "rejected", "revoked", "deleted", "unauthorized"
    @SerialName("state")
    val state: String,
    @SerialName("quoted_status") val quotedStatus: Status? = null,
)