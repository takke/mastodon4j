package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

data class EmojiReaction(
    @SerializedName("name")
    val name: String,

    @SerializedName("count")
    val count: Int,

    @SerializedName("me")
    val me: Boolean?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("static_url")
    val staticUrl: String?,

    @SerializedName("width")
    val width: Int? = null,

    @SerializedName("height")
    val height: Int? = null,

    @SerializedName("domain")
    val domain: String?,
) {
    val toEmoji: Emoji get() = Emoji(name, staticUrl ?: "", url ?: "", false, width, height, null)
}