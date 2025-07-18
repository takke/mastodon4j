//package mastodon4j.api.method
//
//import mastodon4j.MastodonClient
//import mastodon4j.MastodonRequest
//import mastodon4j.Parameter
//import mastodon4j.api.entity.Account
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.RequestBody.Companion.toRequestBody
//
///**
// * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#follows
// */
//class FollowsMethod(private val client: MastodonClient) {
//    /**
//     * POST /api/v1/follows
//     * @param uri: username@domain of the person you want to follow
//     */
//    fun postRemoteFollow(uri: String): MastodonRequest<Account> {
//        val parameters = Parameter()
//            .append("uri", uri)
//            .build()
//        return MastodonRequest<Account>(
//            {
//                client.post(
//                    "/api/v1/follows",
//                    parameters
//                        .toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull())
//                )
//            },
//            {
//                client.getSerializer().fromJson(it, Account::class.java)
//            }
//        )
//    }
//}
