package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

data class DomainBlock(

    @SerializedName("domain") val domain: String = "",
    @SerializedName("digest") val digest: String = "",
    @SerializedName("severity") val severity: String = "",
    @SerializedName("comment") val comment: String = "",
)