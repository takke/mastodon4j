package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Fedibird 拡張: Relationship レスポンス内の account_subscribing マップの値
 *
 * account_subscribing は { "<list_id_or_-1>": { "reblogs": true/false } } の形式で返る。
 * 外側のキーは購読の振り分け先で、list_id が NULL（ホームへの購読）の場合は "-1" になる。
 *
 * see https://github.com/fedibird/mastodon/blob/fedibird/app/presenters/account_relationships_presenter.rb
 */
@Serializable
data class AccountSubscribingEntry(
    @SerialName("reblogs") val reblogs: Boolean = false,
)
