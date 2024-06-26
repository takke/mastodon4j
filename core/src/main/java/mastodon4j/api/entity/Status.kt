package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName
import mastodon4j.api.entity.util.CalckeyCompatUtil
import java.util.*

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#status
 */
class Status(
    @SerializedName("id") val id: String = "",
    @SerializedName("uri") val uri: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("account") val account: Account? = null,
    @SerializedName("in_reply_to_id") val inReplyToId: String? = null,
    @SerializedName("in_reply_to_account_id") val inReplyToAccountId: String? = null,
    @SerializedName("reblog") val reblog: Status? = null,
    @SerializedName("content") val content: String = "",
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("emojis") val emojis: List<Emoji> = emptyList(),
    @SerializedName("replies_count") val repliesCount: Long = 0,
    @SerializedName("reblogs_count") val reblogsCount: Long = 0,
    @SerializedName("favourites_count") val favouritesCount: Long = 0,
    @SerializedName("reblogged") val isReblogged: Boolean = false,
    @SerializedName("favourited") val isFavourited: Boolean = false,
    @SerializedName("bookmarked") val isBookmarked: Boolean = false,
    @SerializedName("sensitive") val isSensitive: Boolean = false,
    @SerializedName("spoiler_text") val spoilerText: String = "",
    // only for delete
    @SerializedName("text") val text: String? = null,
    @SerializedName("visibility") val visibilityValue: String = Visibility.Public.value,
    @SerializedName("visibility_ex") val visibilityExValue: String = "",    // for kmy.blue, fedibird
    @SerializedName("media_attachments") val mediaAttachments: List<MediaAttachment> = emptyList(),
    @SerializedName("mentions") val mentions: List<Mention> = emptyList(),
    @SerializedName("tags") val tags: List<Tag> = emptyList(),
    @SerializedName("application") val application: Application? = null,
    @SerializedName("language") val language: String? = null,
    @SerializedName("edited_at") val editedAt: String? = "",
    @SerializedName("pinned") val pinned: Boolean? = null,
    @SerializedName("card") val card: PreviewCard? = null,
    @SerializedName("poll") val poll: Poll? = null,

    // emoji_reactions of fedibird.com
    @SerializedName("emoji_reactions") val emojiReactions: List<EmojiReaction> = emptyList(),
    @SerializedName("emoji_reactioned") val emojiReactioned: Boolean = false,

    // for fedibird.com
    @SerializedName("status_referred_by_count") val statusReferredByCount: Long = 0,

    // quote of fedibird.com
    @SerializedName("quote") val quote: Status? = null,
) {
    // compat for calckey
    val idAsLong: Long by lazy { CalckeyCompatUtil.toLongOrCalckeyIdOrFakeTimeId(id, createdAt) }

    enum class Visibility(val value: String) {
        Public("public"),
        Unlisted("unlisted"),
        Private("private"),
        Direct("direct"),
        PublicUnlisted("public_unlisted"),  // visibility_ex only (for kmy.blue)
        Personal("personal"),               // visibility_ex only (for fedibird)
        ;

        companion object {
            fun fromString(value: String): Visibility {
                return Visibility.values().firstOrNull { it.value == value } ?: Public
            }

            fun fromStringOrNull(value: String): Visibility? {
                return Visibility.values().firstOrNull { it.value == value }
            }
        }
    }

    val createdAtAsDate: Date? by lazy {
        CalckeyCompatUtil.parseDate(createdAt)
    }

    val visibility: Visibility by lazy {
        Visibility.fromString(visibilityValue)
    }

    val visibilityEx: Visibility by lazy {
        if (visibilityExValue.isEmpty()) return@lazy visibility

        Visibility.fromStringOrNull(visibilityExValue) ?: visibility
    }

    val isEdited: Boolean get() = editedAt?.isNotEmpty() == true
}
