package mastodon4j.api

data class MastodonResponseWrapper<T>(val result: T) : MastodonResponse()
