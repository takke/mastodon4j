package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#results
 */
@Serializable
data class ResultsV1(
    @SerialName("accounts")
    val accounts: List<Account> = emptyList(),

    @SerialName("statuses")
    val statuses: List<Status> = emptyList(),

    @SerialName("hashtags")
    val hashtags: List<String> = emptyList()
)
