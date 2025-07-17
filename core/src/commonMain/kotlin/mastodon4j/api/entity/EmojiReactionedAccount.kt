package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmojiReactionedAccount(
    @SerialName("name")
    val name: String,

    @SerialName("url")
    val url: String?,

    @SerialName("static_url")
    val staticUrl: String?,

    @SerialName("domain")
    val domain: String?,

    @SerialName("width")
    val width: Int? = null,

    @SerialName("height")
    val height: Int? = null,

    @SerialName("account") val account: Account? = null,
)