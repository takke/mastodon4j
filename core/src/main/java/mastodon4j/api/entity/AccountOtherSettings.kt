package mastodon4j.api.entity

import com.google.gson.annotations.SerializedName

class AccountOtherSettings(
    @SerializedName("location") val location: String? = null,
    @SerializedName("birth_day") val birthDay: Int? = null,
    @SerializedName("birth_year") val birthYear: Int? = null,
    @SerializedName("birth_month") val birthMonth: Int? = null,
    @SerializedName("cat_ears_color") val catEarsColor: String? = null,
    @SerializedName("noindex") val noindex: Boolean? = null,
    @SerializedName("hide_network") val hideNetwork: Boolean? = null,
    @SerializedName("hide_statuses_count") val hideStatusesCount: Boolean? = null,
    @SerializedName("hide_following_count") val hideFollowingCount: Boolean? = null,
    @SerializedName("hide_followers_count") val hideFollowersCount: Boolean? = null,
    @SerializedName("enable_reaction") val enableReaction: Boolean? = null,
)