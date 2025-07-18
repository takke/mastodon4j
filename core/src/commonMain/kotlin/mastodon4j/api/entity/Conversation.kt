package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mastodon4j.api.entity.util.CalckeyCompatUtil

// import mastodon4j.api.entity.util.CalckeyCompatUtil

/**
 * see more https://docs.joinmastodon.org/entities/Conversation/
 */
@Serializable
data class Conversation(
    @SerialName("id") val id: String = "",
    @SerialName("unread") val unread: Boolean = false,
    @SerialName("accounts") val accounts: List<Account> = emptyList(),
    @SerialName("last_status") val lastStatus: Status? = null,
) {
    // compat for calckey
    val idAsLong: Long by lazy { CalckeyCompatUtil.toLongOrCalckeyIdOrFakeTimeId(id, "") }
}
