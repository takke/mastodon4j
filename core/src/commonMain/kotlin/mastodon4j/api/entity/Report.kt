package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://docs.joinmastodon.org/entities/Report/
 */
@Serializable
data class Report(
    // The ID of the report
    @SerialName("id")
    val id: String = "",

    // Whether an action was taken yet
    @SerialName("action_taken")
    val actionTaken: Boolean = false,

    // When an action was taken against the report (v4.0.0以降)
    @SerialName("action_taken_at")
    val actionTakenAt: String? = null,

    // The generic reason for the report (spam / legal / violation / other) (v4.0.0以降)
    @SerialName("category")
    val categoryValue: String = Category.Other.value,

    // The reason for the report (v4.0.0以降)
    @SerialName("comment")
    val comment: String = "",

    // Whether the report was forwarded to a remote domain (v4.0.0以降)
    @SerialName("forwarded")
    val forwarded: Boolean = false,

    // When the report was created (v4.0.0以降)
    @SerialName("created_at")
    val createdAt: String = "",

    // IDs of statuses that have been attached to this report for additional context (v4.0.0以降)
    @SerialName("status_ids")
    val statusIds: List<String>? = null,

    // IDs of the rules that have been cited as a violation by this report (v4.0.0以降)
    @SerialName("rule_ids")
    val ruleIds: List<String>? = null,

    // The account that was reported (v4.0.0以降)
    @SerialName("target_account")
    val targetAccount: Account? = null,
) {
    enum class Category(val value: String) {
        // 迷惑行為・繰り返し投稿
        Spam("spam"),

        // 違法なコンテンツ (v4.2.0以降)
        Legal("legal"),

        // サーバールール違反 (rule_ids と併用)
        Violation("violation"),

        // その他
        Other("other"),
    }

    val category: Category?
        get() = Category.entries.firstOrNull { it.value == categoryValue }
}
