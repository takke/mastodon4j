package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#error
 */
@Serializable
data class Error(
    @SerialName("error")
    val error: String = ""
)
