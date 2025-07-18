package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.DomainBlock
import mastodon4j.api.entity.Emoji
import mastodon4j.api.entity.Instance
import mastodon4j.api.entity.Status
import mastodon4j.api.exception.MastodonException

/**
 * 公開情報に関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://docs.joinmastodon.org/methods/instance/
 */
class PublicMethod(private val client: MastodonClient) {

    /**
     * インスタンス情報を取得
     * GET /api/v1/instance
     */
    fun getInstance(): MastodonRequest<Instance> {
        return client.createGetRequest<Instance>("/api/v1/instance")
    }

    /**
     * インスタンスのドメインブロックリストを取得
     * GET /api/v1/instance/domain_blocks
     */
    fun getInstanceDomainBlocks(): MastodonRequest<List<DomainBlock>> {
        return client.createGetRequest<List<DomainBlock>>("/api/v1/instance/domain_blocks")
    }

    /**
     * 公開タイムラインを取得（共通処理）
     */
    private fun getPublic(local: Boolean, range: Range?): MastodonRequest<Pageable<Status>> {
        val params = range?.toParameter() ?: Parameter()
        if (local) {
            params.append("local", local)
        }
        
        val path = "/api/v1/timelines/public?${params.build()}"
        return client.createGetRequest<Pageable<Status>>(path).toPageable()
    }

    /**
     * ローカル公開タイムラインを取得
     * GET /api/v1/timelines/public?local=true
     */
    fun getLocalPublic(range: Range? = null): MastodonRequest<Pageable<Status>> {
        return getPublic(true, range)
    }

    /**
     * 連合公開タイムラインを取得
     * GET /api/v1/timelines/public?local=false
     */
    fun getFederatedPublic(range: Range? = null): MastodonRequest<Pageable<Status>> {
        return getPublic(false, range)
    }

    /**
     * ハッシュタグタイムラインを取得（共通処理）
     */
    private fun getTagTimeline(tag: String, local: Boolean, range: Range?): MastodonRequest<Pageable<Status>> {
        val params = range?.toParameter() ?: Parameter()
        if (local) {
            params.append("local", local)
        }
        
        val path = "/api/v1/timelines/tag/$tag?${params.build()}"
        return client.createGetRequest<Pageable<Status>>(path).toPageable()
    }

    /**
     * ローカルハッシュタグタイムラインを取得
     * GET /api/v1/timelines/tag/:tag?local=true
     */
    fun getLocalTagTimeline(tag: String, range: Range? = null): MastodonRequest<Pageable<Status>> {
        return getTagTimeline(tag, true, range)
    }

    /**
     * 連合ハッシュタグタイムラインを取得
     * GET /api/v1/timelines/tag/:tag?local=false
     */
    fun getFederatedTagTimeline(tag: String, range: Range? = null): MastodonRequest<Pageable<Status>> {
        return getTagTimeline(tag, false, range)
    }

    /**
     * カスタム絵文字リストを取得
     * GET /api/v1/custom_emojis
     */
    fun getCustomEmojis(): MastodonRequest<List<Emoji>> {
        return client.createGetRequest<List<Emoji>>("/api/v1/custom_emojis")
    }
}