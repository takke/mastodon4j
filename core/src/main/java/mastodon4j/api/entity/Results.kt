package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#results
 */
class Results(
    @SerializedName("accounts")
    val accounts: List<Account> = emptyList(),

    @SerializedName("statuses")
    val statuses: List<Status> = emptyList(),

    @SerializedName("hashtags")
    val hashtags: List<Tag> = emptyList()
)
