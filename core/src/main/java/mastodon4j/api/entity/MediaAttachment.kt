package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#attachment
 */
data class MediaAttachment(
    @SerializedName("id") val id: String = "",
    @SerializedName("type") val type: String = Type.Image.value,
    @SerializedName("url") val url: String = "",
    @SerializedName("remote_url") val remoteUrl: String? = null,
    @SerializedName("preview_url") val previewUrl: String? = null,
    @SerializedName("text_url") val textUrl: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("blurhash") val blurHash: String? = null,
) {

    enum class Type(val value: String) {
        Image("image"),
        Video("video"),
        Gifv("gifv"),
        Audio("audio"),
        Unknown("unknwon")
    }
}
