package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

/**
 * see more https://docs.joinmastodon.org/entities/PreviewCard/
 */
data class PreviewCard(
    @SerializedName("url") val url: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("type") val typeValue: String? = null,
    @SerializedName("author_name") val authorName: String? = null,
    @SerializedName("author_url") val authorUrl: String? = null,
    @SerializedName("provider_name") val providerName: String? = null,
    @SerializedName("provider_url") val providerUrl: String? = null,
    @SerializedName("html") val html: String? = null,

    @SerializedName("width") val width: Int? = null,
    @SerializedName("height") val height: Int? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("embed_url") val embedUrl: String? = null,
    @SerializedName("blurhash") val blurhash: String? = null,
) {
    enum class Type(val rawValue: String) {
        Link("link"),
        Photo("photo"),
        Video("video"),
        Rich("rich"),
    }
}
