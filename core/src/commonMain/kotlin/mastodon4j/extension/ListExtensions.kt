package mastodon4j.extension

import io.ktor.client.statement.*
import mastodon4j.api.Link
import mastodon4j.api.Pageable

fun <T> List<T>.toPageable(response: HttpResponse): Pageable<T> {
    val linkHeader = response.headers["link"]
    val link = Link.parse(linkHeader)
    return Pageable(this, link)
}