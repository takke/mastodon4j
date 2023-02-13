package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#instance
 */
data class Instance(
    @SerializedName("uri")
    val uri: String = "",

    @SerializedName("title")
    val title: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("email")
    val email: String = "",

    @SerializedName("version")
    val version: String = "",

    @SerializedName("urls")
    val urls: InstanceUrls? = null,

    @SerializedName("languages")
    val languages: List<String> = emptyList(),

    @SerializedName("contact_account")
    val contactAccount: Account? = null,

    @SerializedName("stats")
    val stats: Stats? = null,


)

data class Stats(
    @SerializedName("user_count")
    val userCount: Long? = null,

    @SerializedName("status_count")
    val statusCount: Long? = null,

    @SerializedName("domain_count")
    val domainCount: Long? = null,

)
