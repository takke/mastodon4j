package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountOtherSettings(
    @SerialName("location") val location: String? = null,
    @SerialName("birth_day") val birthDay: Int? = null,
    @SerialName("birth_year") val birthYear: Int? = null,
    @SerialName("birth_month") val birthMonth: Int? = null,
    @SerialName("cat_ears_color") val catEarsColor: String? = null,
    @SerialName("noindex") val noindex: Boolean? = null,
    @SerialName("hide_network") val hideNetwork: Boolean? = null,
    @SerialName("hide_statuses_count") val hideStatusesCount: Boolean? = null,
    @SerialName("hide_following_count") val hideFollowingCount: Boolean? = null,
    @SerialName("hide_followers_count") val hideFollowersCount: Boolean? = null,
    @SerialName("enable_reaction") val enableReaction: Boolean? = null,
)