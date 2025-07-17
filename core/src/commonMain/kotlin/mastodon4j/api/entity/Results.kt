package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#results
 */
@Serializable
data class Results(
    @SerialName("accounts")
    val accounts: List<Account> = emptyList(),

    @SerialName("statuses")
    val statuses: List<Status> = emptyList(),

    @SerialName("hashtags")
    val hashtags: List<Tag> = emptyList()
)
