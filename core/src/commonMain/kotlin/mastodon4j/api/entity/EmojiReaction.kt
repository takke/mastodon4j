package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmojiReaction(
    @SerialName("name")
    val name: String,

    @SerialName("count")
    val count: Int,

    @SerialName("me")
    val me: Boolean?,

    @SerialName("url")
    val url: String?,

    @SerialName("static_url")
    val staticUrl: String?,

    @SerialName("width")
    val width: Int? = null,

    @SerialName("height")
    val height: Int? = null,

    @SerialName("domain")
    val domain: String?,
) {
    val toEmoji: Emoji get() = Emoji(name, staticUrl ?: "", url ?: "", false, width, height, null)
}