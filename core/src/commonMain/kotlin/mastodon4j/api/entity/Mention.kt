package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#mention
 */
@Serializable
data class Mention(
    @SerialName("url")
    val url: String = "",

    @SerialName("username")
    val username: String = "",

    @SerialName("acct")
    val acct: String = "",

    @SerialName("id")
    val id: String = ""
)
