package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CredentialAccount(
    @SerialName("id") val id: String = "",
    @SerialName("username") val userName: String = "",
    @SerialName("acct") val acct: String = "",
    @SerialName("display_name") val displayName: String = "",
    @SerialName("note") val note: String = "",
    @SerialName("url") val url: String = "",
    @SerialName("avatar") val avatar: String = "",
    @SerialName("header") val header: String = "",
    @SerialName("locked") val isLocked: Boolean = false,
    @SerialName("created_at") val createdAt: String = "",
    @SerialName("followers_count") val followersCount: Long = 0,
    @SerialName("following_count") val followingCount: Long = 0,
    @SerialName("statuses_count") val statusesCount: Long = 0,
    @SerialName("emojis") val emojis: List<Emoji> = emptyList(),
    @SerialName("fields") val fields: List<Field> = emptyList(),
    // same as Account above
    @SerialName("source") val source: Source? = null,
) {
    @Serializable
    data class Source(
        @SerialName("note") val note: String = "",
        @SerialName("fields") val fields: List<Field> = emptyList(),
        @SerialName("privacy") val privacy: String = "",
        @SerialName("sensitive") val isSensitive: Boolean = false,
        @SerialName("language") val language: String = "",
        @SerialName("follow_requests_count") val followRequestsCount: Long = 0,
    )
}