package mastodon4j.extension

import okhttp3.Response
import mastodon4j.api.Link
import mastodon4j.api.Pageable

fun <T> List<T>.toPageable(response: Response): Pageable<T> {
    val linkHeader = response.header("link")
    val link = Link.parse(linkHeader)
    return Pageable(this, link)
}