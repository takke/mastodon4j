package mastodon4j.api

import mastodon4j.api.exception.MastodonException

interface Retryable {
    @Throws(MastodonException::class)
    fun retry()
}