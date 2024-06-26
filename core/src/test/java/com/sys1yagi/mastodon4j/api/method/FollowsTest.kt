package com.sys1yagi.mastodon4j.api.method

import com.sys1yagi.mastodon4j.testtool.MockClient
import mastodon4j.api.exception.MastodonException
import org.amshove.kluent.shouldEqualTo
import org.junit.Assert.*
import org.junit.Test

class FollowsTest {
    @Test
    fun postRemoteFollow() {
        val client = MockClient.mock("follow.json")

        val follows = Follows(client)
        val account = follows.postRemoteFollow("test").execute()
        account.acct shouldEqualTo "test@test.com"
        account.displayName shouldEqualTo "test"
        account.userName shouldEqualTo "test"
    }

    @Test(expected = MastodonException::class)
    fun postRemoteFollowWithException() {
        val client = MockClient.ioException()
        val follows = Follows(client)
        follows.postRemoteFollow("test").execute()
    }

}