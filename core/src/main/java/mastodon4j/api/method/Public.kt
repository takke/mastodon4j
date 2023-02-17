package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Instance
import mastodon4j.api.entity.Status

class Public(private val client: MastodonClient) {
    /**
     * GET /api/v1/instance
     * @see https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#instances
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
     *  GET /api/v1/timelines/public
     * @see https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#timelines
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
     * @see https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#timelines
     */
    private fun getTag(tag: String, local: Boolean, range: Range): MastodonRequest<Pageable<Status>> {
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
    fun getLocalTag(tag: String, range: Range = Range()) = getTag(tag, true, range)

    @JvmOverloads
    fun getFederatedTag(tag: String, range: Range = Range()) = getTag(tag, false, range)
}