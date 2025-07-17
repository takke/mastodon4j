package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#relationship
 */
@Serializable
data class Relationship(
    @SerialName("id") val id: Long = 0L,
    @SerialName("following") val isFollowing: Boolean = false,
    @SerialName("showing_reblogs") val isShowingReblogs: Boolean = false,
    @SerialName("notifying") val isNotifying: Boolean = false,
    @SerialName("followed_by") val isFollowedBy: Boolean = false,
    @SerialName("blocking") val isBlocking: Boolean = false,
    @SerialName("blocked_by") val isBlockedBy: Boolean = false,
    @SerialName("muting") val isMuting: Boolean = false,
    @SerialName("muting_notifications") val isMutingNotifications: Boolean = false,
    @SerialName("requested") val isRequested: Boolean = false,
    @SerialName("domain_blocking") val isDomainBlocking: Boolean = false,
    @SerialName("endorsed") val isEndorsed: Boolean = false,
    @SerialName("note") val note: String? = null,
)
