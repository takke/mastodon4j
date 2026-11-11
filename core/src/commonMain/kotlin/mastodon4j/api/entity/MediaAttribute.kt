package mastodon4j.api.entity

/**
 * ステータス編集時に既存の添付メディアの属性を更新するためのパラメータ
 *
 * PUT /api/v1/statuses/:id の media_attributes[] に対応する
 *
 * @param id 更新対象のメディアID
 * @param description 代替テキスト(null の場合は description を送らない)
 */
data class MediaAttribute(
    val id: String,
    val description: String? = null,
)
