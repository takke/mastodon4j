package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 投稿にマッチしたフィルター (Status.filtered の要素)
 *
 * see https://docs.joinmastodon.org/entities/FilterResult/
 */
@Serializable
data class FilterResult(
    @SerialName("filter") val filter: Filter = Filter(),
    // マッチしたキーワード
    @SerialName("keyword_matches") val keywordMatches: List<String>? = null,
    // マッチした投稿ID
    @SerialName("status_matches") val statusMatches: List<String>? = null,
)
