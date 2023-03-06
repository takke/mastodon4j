package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

data class EmojiReactionedAccount(
    @SerializedName("name")
    val name: String,

    @SerializedName("url")
    val url: String?,

    @SerializedName("static_url")
    val staticUrl: String?,

    @SerializedName("domain")
    val domain: String?,

    @SerializedName("width")
    val width: Int? = null,

    @SerializedName("height")
    val height: Int? = null,

    @SerializedName("account") val account: Account? = null,
)