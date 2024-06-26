package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#tag
 */
data class Tag(
    @SerializedName("name")
    val name: String = "",

    @SerializedName("url")
    val url: String = "",

    @SerializedName("following")
    val following: Boolean = false,

    // TODO support history
)
