package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://docs.joinmastodon.org/entities/PreviewCard/
 */
@Serializable
data class PreviewCard(
    @SerialName("url") val url: String = "",
    @SerialName("title") val title: String = "",
    @SerialName("description") val description: String = "",
    @SerialName("type") val typeValue: String? = null,
    @SerialName("author_name") val authorName: String? = null,
    @SerialName("author_url") val authorUrl: String? = null,
    @SerialName("provider_name") val providerName: String? = null,
    @SerialName("provider_url") val providerUrl: String? = null,
    @SerialName("html") val html: String? = null,

    @SerialName("width") val width: Int? = null,
    @SerialName("height") val height: Int? = null,
    @SerialName("image") val image: String? = null,
    @SerialName("embed_url") val embedUrl: String? = null,
    @SerialName("blurhash") val blurhash: String? = null,
) {
    enum class Type(val rawValue: String) {
        Link("link"),
        Photo("photo"),
        Video("video"),
        Rich("rich"),
    }
}
