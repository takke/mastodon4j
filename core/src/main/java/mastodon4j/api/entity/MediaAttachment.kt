package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName
import mastodon4j.api.entity.util.CalckeyCompatUtil

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#attachment
 */
class MediaAttachment(
    @SerializedName("id") val id_: String = "",
    @SerializedName("type") val type: String = Type.Image.value,
    @SerializedName("url") val url: String = "",
    @SerializedName("remote_url") val remoteUrl: String? = null,
    @SerializedName("preview_url") val previewUrl: String? = null,
    @SerializedName("text_url") val textUrl: String? = null,
    @SerializedName("description") val description: String? = null,
) {
    val id: Long by lazy { CalckeyCompatUtil.toLongOrNull(id_) ?: System.currentTimeMillis() }

    enum class Type(val value: String) {
        Image("image"),
        Video("video"),
        Gifv("gifv"),
        Audio("audio"),
        Unknown("unknwon")
    }
}
