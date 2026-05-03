package mastodon4j.api.exception

import io.ktor.websocket.CloseReason

/**
 * Mastodon Streaming API の WebSocket 接続が異常終了したことを示す例外。
 *
 * `for (frame in incoming)` ループを抜けた後、CloseReason.code が
 * NORMAL / GOING_AWAY 以外の場合に投げられる。
 * 認証エラー（トークン切れ）、サーバー再起動、ポリシー違反などの検出に利用できる。
 *
 * 呼び出し側は `Flow.retryWhen` 等を使い、必要に応じて再接続戦略を実装すること。
 */
class MastodonStreamingException(
    val closeReason: CloseReason?,
    cause: Throwable? = null,
) : Exception(
    "Mastodon Streaming closed: code=${closeReason?.code}, message=${closeReason?.message}",
    cause,
)
