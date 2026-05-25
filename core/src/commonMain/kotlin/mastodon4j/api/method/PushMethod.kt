package mastodon4j.api.method

import androidx.annotation.CheckResult
import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.entity.PushSubscription

/**
 * Web Push subscription 関連の API メソッドクラス。
 *
 * Mastodon は「1 access_token = 1 push subscription」モデルで、POST は毎回上書き（冪等）。
 * See https://docs.joinmastodon.org/methods/push/
 */
class PushMethod(private val client: MastodonClient) {

    /**
     * Push 通知購読を登録（既存があれば上書き）。
     * POST /api/v1/push/subscription
     *
     * @param endpoint Push Proxy / relay の宛先 URL
     * @param p256dh   Web Push 公開鍵 (Base64 URL-safe, NO_PADDING、X9.62 uncompressed point 65 bytes)
     * @param auth     16 バイトの auth secret (Base64 URL-safe, NO_PADDING)
     * @param alerts   通知種別ごとの ON/OFF（デフォルトは [PushSubscription.DEFAULT_ALERTS]）
     * @param policy   配信ポリシー (`"all"` / `"followed"` / `"follower"` / `"none"`)
     */
    @CheckResult
    fun subscribe(
        endpoint: String,
        p256dh: String,
        auth: String,
        alerts: PushSubscription.Alerts = PushSubscription.DEFAULT_ALERTS,
        policy: String? = null,
    ): MastodonRequest<PushSubscription> {
        val params = Parameter().apply {
            append("subscription[endpoint]", endpoint)
            append("subscription[keys][p256dh]", p256dh)
            append("subscription[keys][auth]", auth)
            appendAlerts(alerts)
            policy?.let { append("data[policy]", it) }
        }
        return client.createPostRequest<PushSubscription>("/api/v1/push/subscription", params)
    }

    /**
     * 現在の Push 通知購読を取得（無ければサーバが 404 を返す）。
     * GET /api/v1/push/subscription
     */
    @CheckResult
    fun getSubscription(): MastodonRequest<PushSubscription> {
        return client.createGetRequest<PushSubscription>("/api/v1/push/subscription")
    }

    /**
     * 現在の購読の通知種別/ポリシーを更新（endpoint・鍵は変更しない）。
     * PUT /api/v1/push/subscription
     */
    @CheckResult
    fun updateSubscription(
        alerts: PushSubscription.Alerts,
        policy: String? = null,
    ): MastodonRequest<PushSubscription> {
        val params = Parameter().apply {
            appendAlerts(alerts)
            policy?.let { append("data[policy]", it) }
        }
        return client.createPutRequest<PushSubscription>("/api/v1/push/subscription", params)
    }

    /**
     * 現在の Push 通知購読を削除。
     * DELETE /api/v1/push/subscription
     *
     * Mastodon は 200 OK + `{}` を返す。レスポンスはデフォルト値の [PushSubscription] にパースされる。
     */
    @CheckResult
    fun deleteSubscription(): MastodonRequest<PushSubscription> {
        return client.createDeleteRequest<PushSubscription>("/api/v1/push/subscription")
    }

    // ----- private -----

    private fun Parameter.appendAlerts(alerts: PushSubscription.Alerts) {
        append("data[alerts][mention]", alerts.mention.toString())
        append("data[alerts][favourite]", alerts.favourite.toString())
        append("data[alerts][reblog]", alerts.reblog.toString())
        append("data[alerts][follow]", alerts.follow.toString())
        append("data[alerts][poll]", alerts.poll.toString())
        append("data[alerts][follow_request]", alerts.followRequest.toString())
        append("data[alerts][status]", alerts.status.toString())
        append("data[alerts][update]", alerts.update.toString())
        append("data[alerts][admin.sign_up]", alerts.adminSignUp.toString())
        append("data[alerts][admin.report]", alerts.adminReport.toString())
    }
}
