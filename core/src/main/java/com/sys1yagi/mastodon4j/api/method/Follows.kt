package com.sys1yagi.mastodon4j.api.method

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.MastodonRequest
import com.sys1yagi.mastodon4j.Parameter
import com.sys1yagi.mastodon4j.api.entity.Account
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#follows
 */
class Follows(private val client: MastodonClient) {
    /**
     * POST /api/v1/follows
     * @param uri: username@domain of the person you want to follow
     */
    fun postRemoteFollow(uri: String): MastodonRequest<Account> {
        val parameters = Parameter()
            .append("uri", uri)
            .build()
        return MastodonRequest<Account>(
            {
                client.post(
                    "follows",
                    RequestBody.create(
                        "application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull(),
                        parameters
                    )
                )
            },
            {
                client.getSerializer().fromJson(it, Account::class.java)
            }
        )
    }
}
