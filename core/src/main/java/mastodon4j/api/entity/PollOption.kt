package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

class PollOption(
    @SerializedName("title") val title: String = "",
    @SerializedName("votes_count") val votesCount: Int? = 0
)