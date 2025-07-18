package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Account
import mastodon4j.api.entity.MstList
import mastodon4j.api.entity.MstListRepliesPolicy
import mastodon4j.api.entity.Status

/**
 * リストに関するAPIメソッドクラス（KMP対応版）
 * 
 * See more https://docs.joinmastodon.org/methods/lists/
 */
class ListsMethod(private val client: MastodonClient) {

    /**
     * 作成済みリスト一覧を取得
     * GET /api/v1/lists
     */
    fun getLists(): MastodonRequest<Pageable<MstList>> {
        return client.createGetRequest<Pageable<MstList>>("/api/v1/lists").toPageable()
    }

    /**
     * 指定されたIDのリスト詳細を取得
     * GET /api/v1/lists/:id
     * 
     * @param listId リストのID
     */
    fun getList(listId: Long): MastodonRequest<MstList> {
        return client.createGetRequest<MstList>("/api/v1/lists/$listId")
    }

    /**
     * 指定されたリストのタイムラインを取得
     * GET /api/v1/timelines/list/:list_id
     * 
     * @param listId リストのID
     * @param range ページング用のレンジパラメータ
     */
    fun getListTimeLine(listId: Long, range: Range? = null): MastodonRequest<Pageable<Status>> {
        val path = if (range != null) {
            "/api/v1/timelines/list/$listId?${range.toParameter().build()}"
        } else {
            "/api/v1/timelines/list/$listId"
        }
        return client.createGetRequest<Pageable<Status>>(path).toPageable()
    }

    /**
     * 指定されたリストのメンバーアカウント一覧を取得
     * GET /api/v1/lists/:id/accounts
     * 
     * @param listId リストのID
     * @param range ページング用のレンジパラメータ
     */
    fun getListAccounts(listId: Long, range: Range? = null): MastodonRequest<Pageable<Account>> {
        val path = if (range != null) {
            "/api/v1/lists/$listId/accounts?${range.toParameter().build()}"
        } else {
            "/api/v1/lists/$listId/accounts"
        }
        return client.createGetRequest<Pageable<Account>>(path).toPageable()
    }

    /**
     * 新しいリストを作成
     * POST /api/v1/lists
     * 
     * @param title リストのタイトル
     * @param repliesPolicy リプライポリシー（オプション）
     * @param exclusive 排他的設定（オプション）
     */
    fun createList(
        title: String,
        repliesPolicy: MstListRepliesPolicy? = null,
        exclusive: Boolean? = null,
    ): MastodonRequest<MstList> {
        val params = Parameter().apply {
            append("title", title)
            repliesPolicy?.let { append("replies_policy", it.value) }
            exclusive?.let { append("exclusive", it) }
        }
        return client.createPostRequest<MstList>("/api/v1/lists", params)
    }

    /**
     * 既存のリストを編集
     * PUT /api/v1/lists/:id
     * 
     * @param listId リストのID
     * @param title リストのタイトル
     * @param repliesPolicy リプライポリシー（オプション）
     * @param exclusive 排他的設定（オプション）
     */
    fun editList(
        listId: Long,
        title: String,
        repliesPolicy: MstListRepliesPolicy? = null,
        exclusive: Boolean? = null,
    ): MastodonRequest<MstList> {
        val params = Parameter().apply {
            append("title", title)
            repliesPolicy?.let { append("replies_policy", it.value) }
            exclusive?.let { append("exclusive", it) }
        }
        return client.createPutRequest<MstList>("/api/v1/lists/$listId", params)
    }

    /**
     * リストを削除
     * DELETE /api/v1/lists/:id
     * 
     * @param listId 削除するリストのID
     */
    fun deleteList(listId: Long): MastodonRequest<Unit> {
        return client.createDeleteRequest<Unit>("/api/v1/lists/$listId")
    }

    /**
     * リストにアカウントを追加
     * POST /api/v1/lists/:id/accounts
     * 
     * @param listId リストのID
     * @param accountIds 追加するアカウントIDの配列
     */
    fun addAccountsToList(listId: Long, accountIds: Array<String>): MastodonRequest<Unit> {
        val params = Parameter().apply {
            accountIds.forEach { append("account_ids[]", it) }
        }
        return client.createPostRequest<Unit>("/api/v1/lists/$listId/accounts", params)
    }

    /**
     * リストからアカウントを削除
     * DELETE /api/v1/lists/:id/accounts
     * 
     * @param listId リストのID
     * @param accountIds 削除するアカウントIDの配列
     */
    fun removeAccountsFromList(listId: Long, accountIds: Array<String>): MastodonRequest<Unit> {
        val params = Parameter().apply {
            accountIds.forEach { append("account_ids[]", it) }
        }
        return client.createDeleteRequest<Unit>("/api/v1/lists/$listId/accounts", params)
    }
}