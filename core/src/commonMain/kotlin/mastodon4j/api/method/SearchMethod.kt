package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Range
import mastodon4j.api.entity.Results
import mastodon4j.api.entity.ResultsV1
import mastodon4j.api.exception.MastodonException

/**
 * 検索に関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://docs.joinmastodon.org/methods/search/
 */
class SearchMethod(private val client: MastodonClient) {

    /**
     * 検索（v2 API）
     * GET /api/v2/search
     */
    fun getSearch2(
        query: String,
        resolve: Boolean = false,
        range: Range? = null,
        type: String? = null
    ): MastodonRequest<Results> {
        val params = range?.toParameter() ?: Parameter()
        
        params.append("q", query)
        if (resolve) {
            params.append("resolve", true)
        }
        type?.let { params.append("type", it) }
        
        return client.createGetRequest<Results>("/api/v2/search", params)
    }

    /**
     * 検索（v1 API）
     * GET /api/v1/search
     */
    fun getSearch(query: String, resolve: Boolean = false): MastodonRequest<ResultsV1> {
        val params = Parameter().apply {
            append("q", query)
            if (resolve) {
                append("resolve", resolve)
            }
        }
        
        return client.createGetRequest<ResultsV1>("/api/v1/search", params)
    }
}