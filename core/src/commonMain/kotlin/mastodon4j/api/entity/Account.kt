package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
// import mastodon4j.api.entity.util.CalckeyCompatUtil

@Serializable
data class Account(
    @SerialName("id") val id: String = "",
    @SerialName("username") val userName: String = "",
    @SerialName("acct") val acct: String = "",
    @SerialName("display_name") val displayName_: String? = "", // null when calckey.jp
    @SerialName("note") val note_: String? = "",                // null when calckey.jp
    @SerialName("url") val url: String = "",
    @SerialName("avatar") val avatar: String = "",
    @SerialName("header") val header_: String? = "",            // null when calckey.jp
    @SerialName("locked") val isLocked: Boolean = false,
    @SerialName("created_at") val createdAt: String = "",
    @SerialName("followers_count") val followersCount: Long = 0,
    @SerialName("following_count") val followingCount: Long = 0,
    @SerialName("statuses_count") val statusesCount: Long = 0,
    @SerialName("emojis") val emojis: List<Emoji> = emptyList(),
    @SerialName("fields") val fields: List<Field> = emptyList(),

    // for fedibird.com
    @SerialName("other_settings") val otherSettings: AccountOtherSettings? = null,
) {
    // TODO 対応すること
    // val idAsLong: Long by lazy { CalckeyCompatUtil.toLongOrCalckeyIdOrFakeTimeId(id, createdAt) }

    val displayName: String get() = displayName_ ?: ""
    val note: String get() = note_ ?: ""
    val header: String get() = header_ ?: ""
}
