package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName
import mastodon4j.api.entity.util.CalckeyCompatUtil

class Account(
    @SerializedName("id") val id_: String = "",
    @SerializedName("username") val userName: String = "",
    @SerializedName("acct") val acct: String = "",
    @SerializedName("display_name") val displayName_: String? = "", // null when calckey.jp
    @SerializedName("note") val note_: String? = "",                // null when calckey.jp
    @SerializedName("url") val url: String = "",
    @SerializedName("avatar") val avatar: String = "",
    @SerializedName("header") val header_: String? = "",            // null when calckey.jp
    @SerializedName("locked") val isLocked: Boolean = false,
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("followers_count") val followersCount: Long = 0,
    @SerializedName("following_count") val followingCount: Long = 0,
    @SerializedName("statuses_count") val statusesCount: Long = 0,
    @SerializedName("emojis") val emojis: List<Emoji> = emptyList(),
    @SerializedName("fields") val fields: List<Field> = emptyList(),
) {
    val id: Long by lazy { CalckeyCompatUtil.toLongOrFakeTimeId(id_, createdAt) }

    val displayName: String get() = displayName_ ?: ""
    val note: String get() = note_ ?: ""
    val header: String get() = header_ ?: ""
}
