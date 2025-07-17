package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#context
 */
@Serializable
data class Context(
    @SerialName("ancestors")
    val ancestors: List<Status> = emptyList(),

    @SerialName("descendants")
    val descendants: List<Status> = emptyList()
)
