package mastodon4j.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Mastodon Streaming API の WebSocket フレーム
 *
 * 受信フレーム例:
 * ```json
 * {
 *   "stream": ["user"],
 *   "event": "notification",
 *   "payload": "{\"id\":\"123\",\"type\":\"mention\",...}"
 * }
 * ```
 *
 * payload は JSON 文字列として埋め込まれている（二重エスケープ）。
 * イベント種別ごとに改めてデシリアライズが必要。
 */
@Serializable
internal data class StreamingFrame(
    @SerialName("stream")
    val stream: List<String> = emptyList(),

    @SerialName("event")
    val event: String = "",

    @SerialName("payload")
    val payload: String = ""
)
