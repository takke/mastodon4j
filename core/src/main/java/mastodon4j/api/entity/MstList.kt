package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

class MstList(
    @SerializedName("id") val id: Long = 0L,
    @SerializedName("title") val title: String = "",
    @SerializedName("replies_policy") val repliesPolicyValue: String? = MstListRepliesPolicy.List.value,
)