package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#reports
 */
@Serializable
data class Report(
    @SerialName("id")
    val id: Long = 0L, // The ID of the report
    @SerialName("action_taken")
    val actionTaken: String = "" //	The action taken in response to the report
)
