package mastodon4j.api.method

import androidx.annotation.CheckResult
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
     * ステータスをブーストしたアカウントのリストを取得
     * GET /api/v1/statuses/:id/reblogged_by
     */
    fun getRebloggedBy(statusId: String, range: Range? = null): MastodonRequest<Pageable<Account>> {
        return client.createListGetRequest<Account>(
            path = "/api/v1/statuses/$statusId/reblogged_by",
            parameters = range?.toParameter()
        ).toPageable()
    }

    /**
     * ステータスをお気に入りしたアカウントのリストを取得
     * GET /api/v1/statuses/:id/favourited_by
     */
    fun getFavouritedBy(statusId: String, range: Range? = null): MastodonRequest<Pageable<Account>> {
        return client.createListGetRequest<Account>(
            path = "/api/v1/statuses/$statusId/favourited_by",
            parameters = range?.toParameter()
        ).toPageable()
    }

    /**
     * 新しいステータスを投稿
     * POST /api/v1/statuses
     */
    @CheckResult
    fun postStatus(
        status: String,
        inReplyToId: String? = null,
        mediaIds: List<String>? = null,
        sensitive: Boolean? = null,
        spoilerText: String? = null,
        visibility: Status.Visibility? = null,
        quoteId: String? = null,
        quotedStatusId: String? = null,
        quoteApprovalPolicy: String? = null,
        idempotencyKey: String? = null,
    ): MastodonRequest<Status> {
        val params = Parameter().apply {
            append("status", status)
            inReplyToId?.let { append("in_reply_to_id", it) }
            mediaIds?.let { append("media_ids", it) }
            sensitive?.let { append("sensitive", it) }
            spoilerText?.let { append("spoiler_text", it) }
            visibility?.let { append("visibility", it.value) }
            quoteId?.let { append("quote_id", it) }
            quotedStatusId?.let { append("quoted_status_id", it) }
            quoteApprovalPolicy?.let { append("quote_approval_policy", it) }
        }
        
        val headers = idempotencyKey?.let { mapOf("Idempotency-Key" to it) }

        return client.createPostRequest<Status>("/api/v1/statuses", params, headers)
    }

    /**
     * ステータスを編集
     * PUT /api/v1/statuses/:id
     */
    @CheckResult
    fun editStatus(
        statusId: String,
        status: String,
        spoilerText: String? = null,
        sensitive: Boolean? = null,
        mediaIds: List<String>? = null
    ): MastodonRequest<Status> {
        val params = Parameter().apply {
            append("status", status)
            spoilerText?.let { append("spoiler_text", it) }
            sensitive?.let { append("sensitive", it) }
            mediaIds?.let { append("media_ids", it) }
        }

        return client.createPutRequest<Status>("/api/v1/statuses/$statusId", params)
    }

    /**
     * ステータスを削除
     * DELETE /api/v1/statuses/:id
     */
    @CheckResult
    fun deleteStatus(statusId: String): MastodonRequest<Status> {
        return client.createDeleteRequest<Status>("/api/v1/statuses/$statusId")
    }

    /**
     * ステータスをブースト（リブログ）
     * POST /api/v1/statuses/:id/reblog
     */
    @CheckResult
    fun postReblog(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/reblog")
    }

    /**
     * ステータスのブーストを解除
     * POST /api/v1/statuses/:id/unreblog
     */
    @CheckResult
    fun postUnreblog(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/unreblog")
    }

    /**
     * ステータスをお気に入りに追加
     * POST /api/v1/statuses/:id/favourite
     */
    @CheckResult
    fun postFavourite(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/favourite")
    }

    /**
     * ステータスのお気に入りを解除
     * POST /api/v1/statuses/:id/unfavourite
     */
    @CheckResult
    fun postUnfavourite(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/unfavourite")
    }

    /**
     * ステータスをピン留め
     * POST /api/v1/statuses/:id/pin
     */
    @CheckResult
    fun postPin(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/pin")
    }

    /**
     * ステータスのピン留めを解除
     * POST /api/v1/statuses/:id/unpin
     */
    @CheckResult
    fun postUnpin(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/unpin")
    }

    /**
     * ステータスをブックマークに追加
     * POST /api/v1/statuses/:id/bookmark
     */
    @CheckResult
    fun postBookmark(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/bookmark")
    }

    /**
     * ステータスのブックマークを解除
     * POST /api/v1/statuses/:id/unbookmark
     */
    @CheckResult
    fun postUnbookmark(statusId: String): MastodonRequest<Status> {
        return client.createPostRequest<Status>("/api/v1/statuses/$statusId/unbookmark")
    }

    /**
     * ステータスに絵文字リアクションを追加
     * PUT /api/v1/statuses/:id/emoji_reactions/:emojiName
     */
    suspend fun putEmojiReaction(statusId: String, emojiName: String) {
        val response = client.put("/api/v1/statuses/$statusId/emoji_reactions/$emojiName")
        if (response.status.value !in 200..299) {
            throw MastodonException("Failed to add emoji reaction: ${response.status}")
        }
    }

    /**
     * ステータスの全ての絵文字リアクションを削除
     * POST /api/v1/statuses/:id/emoji_unreactions
     */
    suspend fun deleteAllEmojiReactions(statusId: String) {
        val response = client.post("/api/v1/statuses/$statusId/emoji_unreactions")
        if (response.status.value !in 200..299) {
            throw MastodonException("Failed to delete all emoji reactions: ${response.status}")
        }
    }

    /**
     * ステータスの特定の絵文字リアクションを削除
     * DELETE /api/v1/statuses/:id/emoji_reactions/:emojiName
     */
    suspend fun deleteEmojiReaction(statusId: String, emojiName: String) {
        val response = client.delete("/api/v1/statuses/$statusId/emoji_reactions/$emojiName")
        if (response.status.value !in 200..299) {
            throw MastodonException("Failed to delete emoji reaction: ${response.status}")
        }
    }

    /**
     * ステータスに絵文字リアクションしたユーザーを取得
     * GET /api/v1/statuses/:id/emoji_reactioned_by
     */
    @CheckResult
    fun getEmojiReactionedByUsers(statusId: String, range: Range? = null): MastodonRequest<Pageable<EmojiReactionedAccount>> {
        return client.createListGetRequest<EmojiReactionedAccount>(
            path = "/api/v1/statuses/$statusId/emoji_reactioned_by",
            parameters = range?.toParameter()
        ).toPageable()
    }

    /**
     * 絵文字リアクションのステータス一覧を取得
     * GET /api/v1/emoji_reactions
     */
    fun getEmojiReactions(range: Range? = null): MastodonRequest<Pageable<Status>> {
        return client.createListGetRequest<Status>(
            path = "/api/v1/emoji_reactions",
            parameters = range?.toParameter()
        ).toPageable()
    }

    /**
     * 投票に参加
     * POST /api/v1/polls/:id/votes
     */
    @CheckResult
    fun postPollsVotes(pollId: String, choices: List<Int>): MastodonRequest<Poll> {
        val params = Parameter().apply {
            append("choices", choices)
        }
        return client.createPostRequest<Poll>("/api/v1/polls/$pollId/votes", params)
    }

    /**
     * ステータスのソースを取得（編集用）
     * GET /api/v1/statuses/:id/source
     */
    fun getStatusSource(statusId: String): MastodonRequest<StatusSource> {
        return client.createGetRequest<StatusSource>("/api/v1/statuses/$statusId/source")
    }

    /**
     * ステータスの編集履歴を取得
     * GET /api/v1/statuses/:id/history
     */
    fun getEditHistory(statusId: String): MastodonRequest<List<Status>> {
        return client.createListGetRequest<Status>("/api/v1/statuses/$statusId/history")
    }

}