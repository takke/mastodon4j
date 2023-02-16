package mastodon4j.api

import okhttp3.Response

// TODO interface/impl に分離すること
// TODO headers ではなく rateLimit にすること
abstract class MastodonResponse {

    var code: Int = -1

    var headers: Map<String, String> = emptyMap()


    fun collectResponse(response: Response) {
        this.code = response.code
        this.headers = response.headers.toMap()
    }
}
