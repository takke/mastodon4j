package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#relationship
 */
@Serializable
data class Relationship(
    @SerialName("id") val id: Long = 0L,
    @SerialName("following") val isFollowing: Boolean = false,
    @SerialName("showing_reblogs") val isShowingReblogs: Boolean = false,
    @SerialName("notifying") val isNotifying: Boolean = false,
    @SerialName("followed_by") val isFollowedBy: Boolean = false,
    @SerialName("blocking") val isBlocking: Boolean = false,
    @SerialName("blocked_by") val isBlockedBy: Boolean = false,
    @SerialName("muting") val isMuting: Boolean = false,
    @SerialName("muting_notifications") val isMutingNotifications: Boolean = false,
    @SerialName("requested") val isRequested: Boolean = false,
    @SerialName("domain_blocking") val isDomainBlocking: Boolean = false,
    @SerialName("endorsed") val isEndorsed: Boolean = false,
    @SerialName("note") val note: String? = null,

    // Fedibird 拡張: アカウント購読情報
    // { "<list_id_or_-1>": { "reblogs": true/false } } の形式。
    // 購読していない場合はこのフィールド自体がレスポンスに含まれない。
    // list_id が NULL（ホームへの購読）のときは "-1" がキーになる。
    // see https://github.com/fedibird/mastodon/blob/fedibird/app/presenters/account_relationships_presenter.rb
    @SerialName("account_subscribing") val accountSubscribing: Map<String, AccountSubscribingEntry>? = null,
) {
    /**
     * Fedibird 拡張: 購読中かどうか
     * account_subscribing が空でなければ何らかの形（ホーム or リスト）で購読中。
     */
    val isSubscribing: Boolean
        get() = !accountSubscribing.isNullOrEmpty()
}
