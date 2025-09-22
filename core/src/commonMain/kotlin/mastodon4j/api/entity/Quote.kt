package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = QuoteSerializer::class)
data class Quote(
    // "pending", "accepted", "rejected", "revoked", "deleted", "unauthorized"
    @SerialName("state")
    val state: String,
    @SerialName("quoted_status") val quotedStatus: Status? = null,
)