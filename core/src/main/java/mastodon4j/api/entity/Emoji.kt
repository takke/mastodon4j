package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#emoji
 */
data class Emoji(
    @SerializedName("shortcode")
    val shortcode: String = "",

    @SerializedName("static_url")
    val staticUrl: String = "",

    @SerializedName("url")
    val url: String = "",

    @SerializedName("visible_in_picker")
    val visibleInPicker: Boolean = true,

    @SerializedName("width")
    val width: Int? = null,

    @SerializedName("height")
    val height: Int? = null,

    @SerializedName("category")
    val category: String? = null,

)
