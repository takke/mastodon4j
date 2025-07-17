package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://docs.joinmastodon.org/entities/Account/#Field
 */
@Serializable
data class Field(
    @SerialName("name")
    val name: String = "",

    @SerialName("value")
    val value: String = "",

    @SerialName("verified_at")
    val verified_at: String? = null,
)
