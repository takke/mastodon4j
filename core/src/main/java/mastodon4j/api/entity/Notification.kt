package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#notification
 */
data class Notification(
    // The notification ID
    @SerializedName("id")
    val id: Long = 0L,

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
        Unknown("unknown"),
    }

    val type: Type get() = Type.values().firstOrNull { it.value == this.typeValue } ?: Type.Unknown
}

