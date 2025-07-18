//package mastodon4j.api.method
//
//import mastodon4j.MastodonClient
//import mastodon4j.MastodonRequest
//import mastodon4j.api.Pageable
//import mastodon4j.api.Range
//import mastodon4j.api.entity.Status
//
///**
// * See more https://docs.joinmastodon.org/methods/bookmarks/
// */
//class BookmarksMethod(private val client: MastodonClient) {
//
//    // GET /api/v1/bookmarks
//    @JvmOverloads
//    fun getBookmarks(range: Range = Range()): MastodonRequest<Pageable<Status>> {
//        return MastodonRequest<Pageable<Status>>(
//            {
//                client.get("/api/v1/bookmarks", range.toParameter())
//            },
//            {
//                client.getSerializer().fromJson(it, Status::class.java)
//            }
//        ).toPageable()
//    }
//}
