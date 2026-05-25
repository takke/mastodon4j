package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Mastodon Web Push subscription エンティティ。
 *
 * See https://docs.joinmastodon.org/methods/push/
 */
@Serializable
data class PushSubscription(
    @SerialName("id")
    val id: String = "",

    /** Push 配信の宛先 URL（自前 Push Proxy / relay の endpoint） */
    @SerialName("endpoint")
    val endpoint: String = "",

    /** 通知種別ごとの ON/OFF */
    @SerialName("alerts")
    val alerts: Alerts = Alerts(),

    /** Mastodon インスタンス側 VAPID 公開鍵（Base64 URL-safe） */
    @SerialName("server_key")
    val serverKey: String = "",

    /** 配信ポリシー ("all" / "followed" / "follower" / "none") */
    @SerialName("policy")
    val policy: String? = null,
) {
    /**
     * 通知種別ごとの ON/OFF フラグ。
     *
     * Mastodon API は未指定のフィールドを false にするので、subscribe / update 時は
     * 全フィールドを明示的に送るのが安全。
     */
    @Serializable
    data class Alerts(
        @SerialName("mention")
        val mention: Boolean = false,

        @SerialName("favourite")
        val favourite: Boolean = false,

        @SerialName("reblog")
        val reblog: Boolean = false,

        @SerialName("follow")
        val follow: Boolean = false,

        @SerialName("poll")
        val poll: Boolean = false,

        @SerialName("follow_request")
        val followRequest: Boolean = false,

        @SerialName("status")
        val status: Boolean = false,

        @SerialName("update")
        val update: Boolean = false,

        @SerialName("admin.sign_up")
        val adminSignUp: Boolean = false,

        @SerialName("admin.report")
        val adminReport: Boolean = false,
    )

    companion object {
        /** [Alerts] の常用デフォルト（主要な通知種別を ON）。 */
        val DEFAULT_ALERTS: Alerts = Alerts(
            mention = true,
            favourite = true,
            reblog = true,
            follow = true,
            poll = true,
            followRequest = true,
            status = true,
            update = false,
            adminSignUp = false,
            adminReport = false,
        )

        const val POLICY_ALL = "all"
        const val POLICY_FOLLOWED = "followed"
        const val POLICY_FOLLOWER = "follower"
        const val POLICY_NONE = "none"
    }
}
