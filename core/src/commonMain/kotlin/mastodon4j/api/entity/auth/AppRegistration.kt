package mastodon4j.api.entity.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppRegistration(
        @SerialName("id")
        val id: Long = 0,

        @SerialName("client_id")
        val clientId: String = "",

        @SerialName("client_secret")
        val clientSecret: String = "",

        @SerialName("redirect_uri")
        val redirectUri: String = "",

        var instanceName: String = "") {
}
