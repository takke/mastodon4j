package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

class Poll(

    @SerializedName("id") val id: Long = 0L,
    @SerializedName("expires_at") val expiresAt: String = "",
    @SerializedName("expired") val expired: Boolean = false,
    @SerializedName("multiple") val multiple: Boolean = false,
    @SerializedName("votes_count") val votesCount: Int = 0,
    @SerializedName("voters_count") val votersCount: Int? = null,
    @SerializedName("voted") val voted: Boolean = false,
    @SerializedName("own_votes") val ownVotes: List<Int> = emptyList(),
    @SerializedName("options") val options: List<PollOption> = emptyList(),
    @SerializedName("emojis") val emojis: List<Emoji> = emptyList(),
)