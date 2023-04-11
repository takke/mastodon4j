package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

class CredentialAccount(
    @SerializedName("id") val id: String = "",
    @SerializedName("username") val userName: String = "",
    @SerializedName("acct") val acct: String = "",
    @SerializedName("display_name") val displayName: String = "",
    @SerializedName("note") val note: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("avatar") val avatar: String = "",
    @SerializedName("header") val header: String = "",
    @SerializedName("locked") val isLocked: Boolean = false,
    @SerializedName("created_at") val createdAt: String = "",
    @SerializedName("followers_count") val followersCount: Long = 0,
    @SerializedName("following_count") val followingCount: Long = 0,
    @SerializedName("statuses_count") val statusesCount: Long = 0,
    @SerializedName("emojis") val emojis: List<Emoji> = emptyList(),
    @SerializedName("fields") val fields: List<Field> = emptyList(),
    // same as Account above
    @SerializedName("source") val source: Source? = null,
) {
    class Source(
        @SerializedName("note") val note: String = "",
        @SerializedName("fields") val fields: List<Field> = emptyList(),
        @SerializedName("privacy") val privacy: String = "",
        @SerializedName("sensitive") val isSensitive: Boolean = false,
        @SerializedName("language") val language: String = "",
        @SerializedName("follow_requests_count") val followRequestsCount: Long = 0,
    )
}