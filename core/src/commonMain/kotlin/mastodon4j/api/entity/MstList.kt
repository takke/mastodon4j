package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MstList(
    @SerialName("id") val id: Long = 0L,
    @SerialName("title") val title: String = "",
    @SerialName("replies_policy") val repliesPolicyValue: String? = MstListRepliesPolicy.List.value,
    @SerialName("exclusive") val exclusive: Boolean = false,
) {
    val repliesPolicy: MstListRepliesPolicy
        get() = MstListRepliesPolicy.fromString(repliesPolicyValue)
}