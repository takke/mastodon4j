package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName
import mastodon4j.api.entity.util.CalckeyCompatUtil

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#notification
 */
data class Notification(
    // The notification ID
    @SerializedName("id")
    val id_: String = "",

    // The type of event that resulted in the notification.
    @SerializedName("type")
    val typeValue: String = Type.Mention.value,

    // The timestamp of the notification.
    @SerializedName("created_at") val createdAt: String = "",

    // The account that performed the action that generated the notification.
    @SerializedName("account")
    val account: Account? = null,

    // Status that was the object of the notification.
    // Attached when type of the notification is favourite, reblog, status, mention, poll, or update.
    @SerializedName("status")
    val status: Status? = null,

    // emoji_reaction for fedibird
    @SerializedName("emoji_reaction")
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

    val id: Long by lazy { CalckeyCompatUtil.toLongOrFakeTimeId(id_, createdAt) }

    val type: Type get() = Type.values().firstOrNull { it.value == this.typeValue } ?: Type.Unknown
}

