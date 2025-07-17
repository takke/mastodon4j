package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#emoji
 */
@Serializable
data class Emoji(
    @SerialName("shortcode")
    val shortcode: String = "",

    @SerialName("static_url")
    val staticUrl: String = "",

    @SerialName("url")
    val url: String = "",

    @SerialName("visible_in_picker")
    val visibleInPicker: Boolean = true,

    @SerialName("width")
    val width: Int? = null,

    @SerialName("height")
    val height: Int? = null,

    @SerialName("category")
    val category: String? = null,

    @SerialName("aliases")
    val aliases: List<String> = emptyList()
)
