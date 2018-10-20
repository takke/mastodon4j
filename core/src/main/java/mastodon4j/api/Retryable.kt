package mastodon4j.api

interface Retryable {
    fun retry()
}