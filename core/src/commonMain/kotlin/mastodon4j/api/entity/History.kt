package mastodon4j.api.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * タグの使用統計（通常は過去7日間のデータ）
 * see more https://docs.joinmastodon.org/entities/Tag/#history
 */
@Serializable
data class History(
    // その日の深夜のUNIXタイムスタンプ
    @SerialName("day")
    val day: String = "",

    // その日のタグ使用回数
    @SerialName("uses")
    val uses: String = "",

    // その日にタグを使用したアカウント数
    @SerialName("accounts")
    val accounts: String = "",
)
