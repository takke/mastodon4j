package mastodon4j.api.entity

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mastodon4j.api.entity.util.CalckeyCompatUtil

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#status
 */
@Serializable
data class Status(
    @SerialName("id") val id: String = "",
    @SerialName("uri") val uri: String = "",
    @SerialName("url") val url: String = "",
    @SerialName("account") val account: Account? = null,
    @SerialName("in_reply_to_id") val inReplyToId: String? = null,
    @SerialName("in_reply_to_account_id") val inReplyToAccountId: String? = null,
    @SerialName("reblog") val reblog: Status? = null,
    @SerialName("content") val content: String = "",
    @SerialName("created_at") val createdAt: String = "",
    @SerialName("emojis") val emojis: List<Emoji> = emptyList(),
    @SerialName("replies_count") val repliesCount: Long = 0,
    @SerialName("reblogs_count") val reblogsCount: Long = 0,
    @SerialName("favourites_count") val favouritesCount: Long = 0,
    @SerialName("reblogged") val isReblogged: Boolean = false,
    @SerialName("favourited") val isFavourited: Boolean = false,
    @SerialName("bookmarked") val isBookmarked: Boolean = false,
    @SerialName("sensitive") val isSensitive: Boolean = false,
    @SerialName("spoiler_text") val spoilerText: String = "",
    // only for delete
    @SerialName("text") val text: String? = null,
    @SerialName("visibility") val visibilityValue: String = Visibility.Public.value,
    @SerialName("visibility_ex") val visibilityExValue: String = "",    // for kmy.blue, fedibird
    @SerialName("media_attachments") val mediaAttachments: List<MediaAttachment> = emptyList(),
    @SerialName("mentions") val mentions: List<Mention> = emptyList(),
    @SerialName("tags") val tags: List<Tag> = emptyList(),
    @SerialName("application") val application: Application? = null,
    @SerialName("language") val language: String? = null,
    @SerialName("edited_at") val editedAt: String? = "",
    @SerialName("pinned") val pinned: Boolean? = null,
    @SerialName("card") val card: PreviewCard? = null,
    @SerialName("poll") val poll: Poll? = null,

    // emoji_reactions of fedibird.com
    @SerialName("emoji_reactions") val emojiReactions: List<EmojiReaction> = emptyList(),
    @SerialName("emoji_reactioned") val emojiReactioned: Boolean = false,

    // for fedibird.com
    @SerialName("status_referred_by_count") val statusReferredByCount: Long = 0,

    // quote
    @SerialName("quote") val quote: Quote? = null,
) {
    enum class Visibility(val value: String) {
        Public("public"),
        Unlisted("unlisted"),
        Private("private"),
        Direct("direct"),
        PublicUnlisted("public_unlisted"),  // visibility_ex only (for kmy.blue)
        Personal("personal"),               // visibility_ex only (for fedibird: 自分限定)
        Limited("limited"),                 // visibility_ex only (for fedibird: サークル or 相互フォロー限定)
        ;

        companion object {
            fun fromString(value: String): Visibility {
                return entries.firstOrNull { it.value == value } ?: Public
            }

            fun fromStringOrNull(value: String): Visibility? {
                return entries.firstOrNull { it.value == value }
            }
        }
    }

    val visibility: Visibility by lazy {
        Visibility.fromString(visibilityValue)
    }

    val visibilityEx: Visibility by lazy {
        if (visibilityExValue.isEmpty()) return@lazy visibility

        Visibility.fromStringOrNull(visibilityExValue) ?: visibility
    }

    // compat for calckey
    val idAsLong: Long by lazy { CalckeyCompatUtil.toLongOrCalckeyIdOrFakeTimeId(id, createdAt) }

    val createdAtAsInstant: Instant? by lazy {
        CalckeyCompatUtil.parseDateString(createdAt)
    }

    val isEdited: Boolean get() = editedAt?.isNotEmpty() == true
}
