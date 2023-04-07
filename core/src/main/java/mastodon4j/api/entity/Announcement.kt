package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

/**
 * see more https://docs.joinmastodon.org/entities/Announcement/
 */
data class Announcement(
    @SerializedName("id") val id: Long = 0L,
    @SerializedName("content") val content: String = "",
    @SerializedName("starts_at") val startsAt: String? = null,
    @SerializedName("ends_at") val endsAt: String? = null,
    @SerializedName("published") val published: Boolean = false,
    @SerializedName("all_day") val allDay: Boolean = false,
    @SerializedName("published_at") val publishedAt: String = "",
    @SerializedName("updated_at") val updatedAt: String = "",
    @SerializedName("read") val read: Boolean? = null,
    @SerializedName("mentions") val mentions: List<AnnouncementAccount> = emptyList(),
    @SerializedName("statuses") val statuses: List<AnnouncementStatus> = emptyList(),
    @SerializedName("tags") val tags: List<Tag> = emptyList(),
    @SerializedName("emojis") val emojis: List<Emoji> = emptyList(),
    @SerializedName("reactions") val reactions: List<EmojiReaction> = emptyList()
)

class AnnouncementAccount(
    @SerializedName("id") val id: Long = 0L,
    @SerializedName("username") val userName: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("acct") val acct: String = "",
)

class AnnouncementStatus(
    @SerializedName("id") val id: Long = 0L,
    @SerializedName("url") val url: String = "",
)