package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#attachment
 */
@Serializable
data class MediaAttachment(
    @SerialName("id") val id: String = "",
    @SerialName("type") val type: String = Type.Image.value,
    @SerialName("url") val url: String = "",
    @SerialName("remote_url") val remoteUrl: String? = null,
    @SerialName("preview_url") val previewUrl: String? = null,
    @SerialName("text_url") val textUrl: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("blurhash") val blurHash: String? = null,
    @SerialName("meta") val meta: Meta? = null,
) {

    enum class Type(val value: String) {
        Image("image"),
        Video("video"),
        Gifv("gifv"),
        Audio("audio"),
        Unknown("unknwon")
    }

    /**
     * メディアのメタ情報
     */
    @Serializable
    data class Meta(
        @SerialName("original") val original: MetaSize? = null,
        @SerialName("small") val small: MetaSize? = null,
    )

    /**
     * メディアのサイズ情報
     */
    @Serializable
    data class MetaSize(
        @SerialName("width") val width: Int? = null,
        @SerialName("height") val height: Int? = null,
        @SerialName("aspect") val aspect: Double? = null,
    )
}
