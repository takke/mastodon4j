package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmojiReaction(
    @SerialName("name")
    val name: String = "",

    @SerialName("count")
    val count: Int = 0,

    @SerialName("me")
    val me: Boolean? = null,

    @SerialName("url")
    val url: String? = null,

    @SerialName("static_url")
    val staticUrl: String? = null,

    @SerialName("width")
    val width: Int? = null,

    @SerialName("height")
    val height: Int? = null,

    @SerialName("domain")
    val domain: String? = null,
) {
    val toEmoji: Emoji get() = Emoji(name, staticUrl ?: "", url ?: "", false, width, height, null)
}