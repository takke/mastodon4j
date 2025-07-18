package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mastodon4j.api.entity.util.CalckeyCompatUtil

// import mastodon4j.api.entity.util.CalckeyCompatUtil

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#notification
 */
@Serializable
data class Notification(
    // The notification ID
    @SerialName("id")
    val id: String = "",

    // The type of event that resulted in the notification.
    @SerialName("type")
    val typeValue: String = Type.Mention.value,

    // The timestamp of the notification.
    @SerialName("created_at") val createdAt: String = "",

    // The account that performed the action that generated the notification.
    @SerialName("account")
    val account: Account? = null,

    // Status that was the object of the notification.
    // Attached when type of the notification is favourite, reblog, status, mention, poll, or update.
    @SerialName("status")
    val status: Status? = null,

    // emoji_reaction for fedibird
    @SerialName("emoji_reaction")
    val emojiReaction: EmojiReaction? = null,

) {
    enum class Type(val value: String) {
        Mention("mention"),
        Status("status"),
        Reblog("reblog"),
        Follow("follow"),
        FollowRequest("follow_request"),
        Favourite("favourite"),
        Poll("poll"),
        Update("update"),
        EmojiReaction("emoji_reaction"),
        StatusReference("status_reference"),
        AdminSignup("admin.sign_up"),
        AdminReport("admin.report"),
        Unknown("unknown"),
    }

    // compat for calckey
    val idAsLong: Long by lazy { CalckeyCompatUtil.toLongOrCalckeyIdOrFakeTimeId(id, createdAt) }

    val type: Type get() = Type.values().firstOrNull { it.value == this.typeValue } ?: Type.Unknown
}

