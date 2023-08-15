package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * see more https://docs.joinmastodon.org/entities/StatusSource/
 */
class StatusSource(
    @SerializedName("id") val id: String = "",
    @SerializedName("text") val text: String = "",
    @SerializedName("spoiler_text") val spoilerText: String = "",
)