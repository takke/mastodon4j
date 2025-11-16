package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 引用投稿の承認情報を表すエンティティ
 * see more https://docs.joinmastodon.org/entities/QuoteApproval/
 */
@Serializable
data class QuoteApproval(
    // 自動的に承認される引用を可能にする対象
    // "public", "followers", "following", "unsupported_policy" の値を含む
    @SerialName("automatic") val automatic: List<String> = emptyList(),

    // 手動レビュー後に承認される引用を可能にする対象
    // "public", "followers", "following", "unsupported_policy" の値を含む
    @SerialName("manual") val manual: List<String> = emptyList(),

    // この投稿の引用ポリシーが現在のユーザーにどのように適用されるか
    // "automatic", "manual", "denied", "unknown" の値を取る
    @SerialName("current_user") val currentUser: String = "",
)
