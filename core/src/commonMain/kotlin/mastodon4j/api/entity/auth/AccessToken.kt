package mastodon4j.api.entity.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AccessToken(
    @SerialName("access_token")
    var accessToken: String = "",

    @SerialName("token_type")
    var tokenType: String = "",

    @SerialName("scope")
    var scope: String = "",

    @SerialName("created_at")
    var createdAt: Long = 0L
)
