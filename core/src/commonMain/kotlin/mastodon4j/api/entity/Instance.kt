package mastodon4j.api.entity

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#instance
 */
@Serializable
data class Instance(
    @SerialName("uri")
    val uri: String = "",

    @SerialName("domain")
    val domain: String = "",

    @SerialName("title")
    val title: String = "",

    @SerialName("description")
    val description: String = "",

    @SerialName("short_description")
    val shortDescription: String = "",

    @SerialName("email")
    val email: String = "",

    @SerialName("version")
    val version: String = "",

    @SerialName("urls")
    val urls: InstanceUrls? = null,

    @SerialName("languages")
    val languages: List<String> = emptyList(),

    @SerialName("contact_account")
    val contactAccount: Account? = null,

    @SerialName("stats")
    val stats: Stats? = null,

    @SerialName("thumbnail")
    @Serializable(with = ThumbnailSerializer::class)
    val thumbnail: Thumbnail? = null,

    @SerialName("configuration")
    val configuration: Configuration? = null,

    @SerialName("feature_quote")
    val featureQuote: Boolean = false,

    @SerialName("fedibird_capabilities")
    val fedibirdCapabilitiesList: List<String> = emptyList(),
) {
    val fedibirdCapabilities: FedibirdCapabilities
        get() = FedibirdCapabilities(fedibirdCapabilitiesList)

    /**
     * サムネイル情報
     * 文字列形式（古いAPI）とオブジェクト形式（新しいAPI）の両方に対応
     */
    @Serializable
    data class Thumbnail(
        @SerialName("url")
        val url: String,

        @SerialName("blurhash")
        val blurhash: String? = null,

        @SerialName("versions")
        val versions: Versions? = null,
    ) {
        @Serializable
        data class Versions(
            @SerialName("@1x")
            val at1x: String? = null,

            @SerialName("@2x")
            val at2x: String? = null,
        )
    }

    @Serializable
    data class Stats(
        @SerialName("user_count")
        val userCount: Long? = null,

        @SerialName("status_count")
        val statusCount: Long? = null,

        @SerialName("domain_count")
        val domainCount: Long? = null,
    )

    @Serializable
    data class Configuration(
        @SerialName("statuses")
        val statuses: Statuses? = null,

        @SerialName("accounts")
        val accounts: Accounts? = null,

        @SerialName("emoji_reactions")
        val emojiReactions: EmojiReactions? = null,
    ) {
        @Serializable
        data class Statuses(
            @SerialName("max_characters")
            val maxCharacters: Int? = null,
        )

        @Serializable
        data class Accounts(
            @SerialName("max_profile_fields")
            val maxProfileFields: Int? = null,
        )

        @Serializable
        data class EmojiReactions(
            @SerialName("max_reactions")
            val maxReactions: Int? = null,

            @SerialName("max_reactions_per_account")
            val maxReactionsPerAccount: Int? = null,
        )
    }

    @Serializable
    data class FedibirdCapabilities(
        private val capabilities: List<String> = emptyList(),
    ) {
        val favouriteHashtag: Boolean get() = capabilities.contains("favourite_hashtag")
        val favouriteDomain: Boolean get() = capabilities.contains("favourite_domain")
        val favouriteList: Boolean get() = capabilities.contains("favourite_list")
        val statusExpire: Boolean get() = capabilities.contains("status_expire")
        val followNoDelivery: Boolean get() = capabilities.contains("follow_no_delivery")
        val followHashtag: Boolean get() = capabilities.contains("follow_hashtag")
        val subscribeAccount: Boolean get() = capabilities.contains("subscribe_account")
        val subscribeDomain: Boolean get() = capabilities.contains("subscribe_domain")
        val subscribeKeyword: Boolean get() = capabilities.contains("subscribe_keyword")
        val timelineHomeVisibility: Boolean get() = capabilities.contains("timeline_home_visibility")
        val timelineNoLocal: Boolean get() = capabilities.contains("timeline_no_local")
        val timelineDomain: Boolean get() = capabilities.contains("timeline_domain")
        val timelineGroup: Boolean get() = capabilities.contains("timeline_group")
        val timelineGroupDirectory: Boolean get() = capabilities.contains("timeline_group_directory")
        val visibilityMutual: Boolean get() = capabilities.contains("visibility_mutual")
        val visibilityLimited: Boolean get() = capabilities.contains("visibility_limited")
        val visibilityPersonal: Boolean get() = capabilities.contains("visibility_personal")
        val emojiReaction: Boolean get() = capabilities.contains("emoji_reaction")
        val misskeyBirthday: Boolean get() = capabilities.contains("misskey_birthday")
        val misskeyLocation: Boolean get() = capabilities.contains("misskey_location")
        val statusReference: Boolean get() = capabilities.contains("status_reference")
        val searchability: Boolean get() = capabilities.contains("searchability")
        val statusCompactMode: Boolean get() = capabilities.contains("status_compact_mode")
        val accountConversations: Boolean get() = capabilities.contains("account_conversations")
        val enableWideEmoji: Boolean get() = capabilities.contains("enable_wide_emoji")
        val enableWideEmojiReaction: Boolean get() = capabilities.contains("enable_wide_emoji_reaction")
        val timelineBookmarkMediaOption: Boolean get() = capabilities.contains("timeline_bookmark_media_option")
        val timelineFavouriteMediaOption: Boolean get() = capabilities.contains("timeline_favourite_media_option")
        val timelineEmojiReactionMediaOption: Boolean get() = capabilities.contains("timeline_emoji_reaction_media_option")
        val timelinePersonalMediaOption: Boolean get() = capabilities.contains("timeline_personal_media_option")
        val kmyblueVisibilityPublicUnlisted: Boolean get() = capabilities.contains("kmyblue_visibility_public_unlisted")
    }
}

/**
 * Thumbnailのカスタムシリアライザー
 * 文字列形式（古いAPI）とオブジェクト形式（新しいAPI）の両方に対応
 */
object ThumbnailSerializer : KSerializer<Instance.Thumbnail?> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Thumbnail") {
        element<String?>("url")
        element<String?>("blurhash")
        element<Instance.Thumbnail.Versions?>("versions")
    }

    override fun deserialize(decoder: Decoder): Instance.Thumbnail? {
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw IllegalArgumentException("This serializer can only be used with JSON")

        val element = jsonDecoder.decodeJsonElement()

        return when {
            // nullの場合
            element is JsonPrimitive && element.isString && element.content == "null" -> null
            // 文字列形式（古いAPI）の場合
            element is JsonPrimitive && element.isString -> {
                Instance.Thumbnail(url = element.content)
            }
            // オブジェクト形式（新しいAPI）の場合
            element is JsonObject -> {
                val url = element["url"]?.jsonPrimitive?.content ?: ""
                val blurhash = element["blurhash"]?.jsonPrimitive?.content
                val versions = element["versions"]?.let { versionsElement ->
                    if (versionsElement is JsonObject) {
                        Instance.Thumbnail.Versions(
                            at1x = versionsElement["@1x"]?.jsonPrimitive?.content,
                            at2x = versionsElement["@2x"]?.jsonPrimitive?.content
                        )
                    } else null
                }
                Instance.Thumbnail(url = url, blurhash = blurhash, versions = versions)
            }

            else -> null
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(encoder: Encoder, value: Instance.Thumbnail?) {
        if (value == null) {
            encoder.encodeNull()
        } else {
            // オブジェクト形式でシリアライズ
            encoder.encodeSerializableValue(Instance.Thumbnail.serializer(), value)
        }
    }
}

