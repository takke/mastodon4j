package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.DomainBlock
import mastodon4j.api.entity.Emoji
import mastodon4j.api.entity.Instance
import mastodon4j.api.entity.Status
import mastodon4j.api.exception.MastodonException

class PublicMethod(private val client: MastodonClient) {

    /**
     * GET /api/v1/instance
     *
     * @see https://docs.joinmastodon.org/methods/instance/
     */
    fun getInstance(): MastodonRequest<Instance> {
        return MastodonRequest(
            {
                client.get("/api/v1/instance")
            },
            { json ->
                client.getSerializer().fromJson(json, Instance::class.java)
            }
        )
    }

    /**
     * GET /api/v1/instance/domain_blocks
     *
     * @see https://docs.joinmastodon.org/methods/instance/
     */
    fun getInstanceDomainBlocks(): MastodonRequest<List<DomainBlock>> {
        return MastodonRequest(
            {
                client.get("/api/v1/instance/domain_blocks")
            },
            {
                client.getSerializer().fromJson(it, DomainBlock::class.java)
            }
        )
    }

    /**
     * GET /api/v1/timelines/public
     *
     * @see https://docs.joinmastodon.org/methods/instance/
     */
    private fun getPublic(local: Boolean, range: Range): MastodonRequest<Pageable<Status>> {
        val parameter = range.toParameter()
        if (local) {
            parameter.append("local", local)
        }
        return MastodonRequest<Pageable<Status>>(
            {
                client.get("/api/v1/timelines/public", parameter)
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        ).toPageable()
    }

    @JvmOverloads
    fun getLocalPublic(range: Range = Range()) = getPublic(true, range)

    @JvmOverloads
    fun getFederatedPublic(range: Range = Range()) = getPublic(false, range)

    /**
     * GET /api/v1/timelines/tag/:tag
     *
     * @see https://docs.joinmastodon.org/methods/instance/
     */
    private fun getTagTimeline(tag: String, local: Boolean, range: Range): MastodonRequest<Pageable<Status>> {
        val parameter = range.toParameter()
        if (local) {
            parameter.append("local", local)
        }
        return MastodonRequest<Pageable<Status>>(
            {
                client.get(
                    "/api/v1/timelines/tag/$tag",
                    parameter
                )
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        ).toPageable()
    }

    @JvmOverloads
    fun getLocalTagTimeline(tag: String, range: Range = Range()) = getTagTimeline(tag, true, range)

    @JvmOverloads
    fun getFederatedTagTimeline(tag: String, range: Range = Range()) = getTagTimeline(tag, false, range)

    /**
     * GET /api/v1/custom_emojis
     *
     * @see https://docs.joinmastodon.org/methods/custom_emojis/
     */
    @Throws(MastodonException::class)
    fun getCustomEmojis(): MastodonRequest<List<Emoji>> {
        return MastodonRequest(
            {
                client.get("/api/v1/custom_emojis")
            },
            {
                client.getSerializer().fromJson(it, Emoji::class.java)
            }
        )
    }
}