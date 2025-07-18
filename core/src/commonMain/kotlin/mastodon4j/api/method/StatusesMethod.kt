package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.*
import mastodon4j.api.exception.MastodonException

/**
 * Statusesに関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#statuses
 */
class StatusesMethod(private val client: MastodonClient) {

    /**
     * 指定されたIDのステータスを取得
     * GET /api/v1/statuses/:id
     */
    fun getStatus(statusId: String): MastodonRequest<Status> {
        return client.createGetRequest<Status>("/api/v1/statuses/$statusId")
    }

    /**
     * 指定されたステータスのコンテキスト（返信・リプライチェーン）を取得
     * GET /api/v1/statuses/:id/context
     */
    fun getContext(statusId: String): MastodonRequest<Context> {
        return client.createGetRequest<Context>("/api/v1/statuses/$statusId/context")
    }

    /**
     * ステータスをお気に入りに追加
     * POST /api/v1/statuses/:id/favourite
     */
    fun postFavourite(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/favourite")
    }

    /**
     * ステータスのお気に入りを解除
     * POST /api/v1/statuses/:id/unfavourite
     */
    fun postUnfavourite(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/unfavourite")
    }

    /**
     * ステータスをブースト（リブログ）
     * POST /api/v1/statuses/:id/reblog
     */
    fun postReblog(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/reblog")
    }

    /**
     * ステータスのブーストを解除
     * POST /api/v1/statuses/:id/unreblog
     */
    fun postUnreblog(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/unreblog")
    }

    /**
     * ステータスをブックマークに追加
     * POST /api/v1/statuses/:id/bookmark
     */
    fun postBookmark(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/bookmark")
    }

    /**
     * ステータスのブックマークを解除
     * POST /api/v1/statuses/:id/unbookmark
     */
    fun postUnbookmark(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/unbookmark")
    }

    /**
     * ステータスを削除
     * DELETE /api/v1/statuses/:id
     */
    fun deleteStatus(statusId: String): MastodonRequest<Status> {
        return client.createDeleteRequest<Status>("/api/v1/statuses/$statusId")
    }

    /**
     * 新しいステータスを投稿
     * POST /api/v1/statuses
     */
    fun postStatus(
        status: String,
        inReplyToId: String? = null,
        mediaIds: List<String>? = null,
        sensitive: Boolean? = null,
        spoilerText: String? = null,
        visibility: Status.Visibility? = null
    ): MastodonRequest<Status> {
        val params = Parameter().apply {
            append("status", status)
            inReplyToId?.let { append("in_reply_to_id", it) }
            mediaIds?.let { append("media_ids", it) }
            sensitive?.let { append("sensitive", it) }
            spoilerText?.let { append("spoiler_text", it) }
            visibility?.let { append("visibility", it.value) }
        }
        
        return client.createPostRequest<Status>("/api/v1/statuses", params)
    }
}