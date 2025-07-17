package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://docs.joinmastodon.org/entities/Announcement/
 */
@Serializable
data class Announcement(
    @SerialName("id") val id: Long = 0L,
    @SerialName("content") val content: String = "",
    @SerialName("starts_at") val startsAt: String? = null,
    @SerialName("ends_at") val endsAt: String? = null,
    @SerialName("published") val published: Boolean = false,
    @SerialName("all_day") val allDay: Boolean = false,
    @SerialName("published_at") val publishedAt: String = "",
    @SerialName("updated_at") val updatedAt: String = "",
    @SerialName("read") val read: Boolean? = null,
    @SerialName("mentions") val mentions: List<AnnouncementAccount> = emptyList(),
    @SerialName("statuses") val statuses: List<AnnouncementStatus> = emptyList(),
    @SerialName("tags") val tags: List<Tag> = emptyList(),
    @SerialName("emojis") val emojis: List<Emoji> = emptyList(),
    @SerialName("reactions") val reactions: List<EmojiReaction> = emptyList()
)

@Serializable
data class AnnouncementAccount(
    @SerialName("id") val id: Long = 0L,
    @SerialName("username") val userName: String = "",
    @SerialName("url") val url: String = "",
    @SerialName("acct") val acct: String = "",
)

@Serializable
data class AnnouncementStatus(
    @SerialName("id") val id: Long = 0L,
    @SerialName("url") val url: String = "",
)