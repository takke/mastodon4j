package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#tag
 */
@Serializable
data class Tag(
    @SerialName("name")
    val name: String = "",

    @SerialName("url")
    val url: String = "",

    @SerialName("following")
    val following: Boolean = false,

    // タグの使用統計（通常は過去7日間のデータ）
    @SerialName("history")
    val history: List<History> = emptyList(),
)
