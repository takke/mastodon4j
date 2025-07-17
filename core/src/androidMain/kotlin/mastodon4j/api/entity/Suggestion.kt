package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

class Suggestion(
    @SerializedName("source") val source: String = "",  // staff, past_interactions, global
    @SerializedName("account") val account: Account,
)
