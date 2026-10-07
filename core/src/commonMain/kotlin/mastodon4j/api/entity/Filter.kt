package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mastodon4j.api.entity.util.CalckeyCompatUtil
import kotlin.time.Instant

/**
 * フィルター (Filters v2, Mastodon 4.0+ / fedibird.com の filter_v2)
 *
 * see https://docs.joinmastodon.org/entities/Filter/
 */
@Serializable
data class Filter(
    @SerialName("id") val id: String = "",
    @SerialName("title") val title: String = "",
    // 適用先 (home / notifications / public / thread / account)
    @SerialName("context") val contextValues: List<String> = emptyList(),
    // 無期限の場合は null
    @SerialName("expires_at") val expiresAt: String? = null,
    // warn / hide / blur
    @SerialName("filter_action") val filterActionValue: String = Action.Warn.value,
    // FilterResult 内の filter では省略される
    @SerialName("keywords") val keywords: List<FilterKeyword> = emptyList(),
    // FilterResult 内の filter では省略される
    @SerialName("statuses") val statuses: List<FilterStatus> = emptyList(),
) {
    enum class Action(val value: String) {
        // 理由を表示して折りたたむ
        Warn("warn"),
        // 完全に非表示にする
        Hide("hide"),
        // メディアのみぼかす (Mastodon 4.4+)
        Blur("blur"),
        ;

        companion object {
            fun fromStringOrNull(value: String): Action? {
                return entries.firstOrNull { it.value == value }
            }
        }
    }

    enum class Context(val value: String) {
        Home("home"),           // ホームタイムラインとリスト
        Notifications("notifications"),
        Public("public"),       // 公開タイムライン
        Thread("thread"),       // 会話・投稿詳細
        Account("account"),     // プロフィール
        ;

        companion object {
            fun fromStringOrNull(value: String): Context? {
                return entries.firstOrNull { it.value == value }
            }
        }
    }

    // 未知の値は本家 WebUI と同様に warn として扱う (hide 以外は折りたたみ表示)
    val filterAction: Action by lazy {
        Action.fromStringOrNull(filterActionValue) ?: Action.Warn
    }

    // 未知の値は除外する
    val contexts: Set<Context> by lazy {
        contextValues.mapNotNull { Context.fromStringOrNull(it) }.toSet()
    }

    val expiresAtAsInstant: Instant? by lazy {
        expiresAt?.let { CalckeyCompatUtil.parseDateString(it) }
    }

    /**
     * 指定時刻の時点で期限切れかどうか (無期限なら常に false)
     */
    fun isExpired(now: Instant): Boolean {
        val expiresAtInstant = expiresAtAsInstant ?: return false
        return expiresAtInstant <= now
    }
}
