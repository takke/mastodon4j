package mastodon4j.extension

import mastodon4j.api.Pageable
import okhttp3.Response

fun <T> List<T>.toPageable(response: Response): Pageable<T> {
    val linkHeader = response.header("link")
    val link = mastodon4j.api.Link.parse(linkHeader)
    return Pageable(this, link)
}
