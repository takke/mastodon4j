package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#application
 */
@Serializable
data class Application(
    @SerialName("name")
    val name: String = "",

    @SerialName("website")
    val website: String? = null
)
