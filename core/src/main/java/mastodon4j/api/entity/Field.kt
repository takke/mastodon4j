package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

/**
 * see more https://docs.joinmastodon.org/entities/Account/#Field
 */
class Field(
    @SerializedName("name")
    val name: String = "",

    @SerializedName("value")
    val value: String = "",

    @SerializedName("verified_at")
    val verified_at: String? = null,
)
