package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DomainBlock(

    @SerialName("domain") val domain: String = "",
    @SerialName("digest") val digest: String = "",
    @SerialName("severity") val severity: String = "",
    @SerialName("comment") val comment: String = "",
)