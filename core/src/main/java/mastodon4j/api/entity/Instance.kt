package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#instance
 */
data class Instance(
    @SerializedName("uri")
    val uri: String = "",

    @SerializedName("title")
    val title: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("short_description")
    val shortDescription: String = "",

    @SerializedName("email")
    val email: String = "",

    @SerializedName("version")
    val version: String = "",

    @SerializedName("urls")
    val urls: InstanceUrls? = null,

    @SerializedName("languages")
    val languages: List<String> = emptyList(),

    @SerializedName("contact_account")
    val contactAccount: Account? = null,

    @SerializedName("stats")
    val stats: Stats? = null,

    @SerializedName("thumbnail")
    val thumbnail: String? = null,

    @SerializedName("configuration")
    val configuration: Configuration? = null,

    @SerializedName("feature_quote")
    val featureQuote: Boolean = false,

    @SerializedName("fedibird_capabilities")
    val fedibirdCapabilitiesList: List<String> = emptyList(),
) {
    val fedibirdCapabilities: FedibirdCapabilities
        get() = FedibirdCapabilities(fedibirdCapabilitiesList)

    data class Stats(
        @SerializedName("user_count")
        val userCount: Long? = null,

        @SerializedName("status_count")
        val statusCount: Long? = null,

        @SerializedName("domain_count")
        val domainCount: Long? = null,
    )

    data class Configuration(
        @SerializedName("statuses")
        val statuses: Statuses? = null,
    ) {
        data class Statuses(
            @SerializedName("max_characters")
            val maxCharacters: Int? = null,
        )
    }

    class FedibirdCapabilities(
        private val capabilities: List<String>,
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
    }
}

