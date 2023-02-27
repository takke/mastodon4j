package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#relationship
 */
data class Relationship(
    @SerializedName("id") val id: Long = 0L,
    @SerializedName("following") val isFollowing: Boolean = false,
    @SerializedName("showing_reblogs") val isShowingReblogs: Boolean = false,
    @SerializedName("notifying") val isNotifying: Boolean = false,
    @SerializedName("followed_by") val isFollowedBy: Boolean = false,
    @SerializedName("blocking") val isBlocking: Boolean = false,
    @SerializedName("blocked_by") val isBlockedBy: Boolean = false,
    @SerializedName("muting") val isMuting: Boolean = false,
    @SerializedName("muting_notifications") val isMutingNotifications: Boolean = false,
    @SerializedName("requested") val isRequested: Boolean = false,
    @SerializedName("domain_blocking") val isDomainBlocking: Boolean = false,
    @SerializedName("endorsed") val isEndorsed: Boolean = false,
    @SerializedName("note") val note: String? = null,
)
