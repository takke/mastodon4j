package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * フィルターのキーワード
 *
 * see https://docs.joinmastodon.org/entities/FilterKeyword/
 */
@Serializable
data class FilterKeyword(
    @SerialName("id") val id: String = "",
    @SerialName("keyword") val keyword: String = "",
    // 単語全体にマッチ (日本語では効かないため fedibird.com では初期値 OFF)
    @SerialName("whole_word") val wholeWord: Boolean = false,
)
