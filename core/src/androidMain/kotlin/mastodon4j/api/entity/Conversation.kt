package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName
import mastodon4j.api.entity.util.CalckeyCompatUtil

/**
 * see more https://docs.joinmastodon.org/entities/Conversation/
 */
class Conversation(
    @SerializedName("id") val id: String = "",
    @SerializedName("unread") val unread: Boolean = false,
    @SerializedName("accounts") val accounts: List<Account> = emptyList(),
    @SerializedName("last_status") val lastStatus: Status? = null,
) {
    // compat for calckey
    val idAsLong: Long by lazy { CalckeyCompatUtil.toLongOrCalckeyIdOrFakeTimeId(id, "") }
}
