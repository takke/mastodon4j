package com.sys1yagi.mastodon4j.api.method

import com.sys1yagi.mastodon4j.testtool.MockClient
import mastodon4j.api.exception.MastodonException
import org.amshove.kluent.shouldEqualTo
import org.junit.Assert.*
import org.junit.Test

class MutesTest {
    @Test
    fun getMutes() {
        val client = MockClient.mock("mutes.json")
        val mutes = Mutes(client)
        val pageable = mutes.getMutes().execute()
        val account = pageable.part.first()
        account.acct shouldEqualTo "test@test.com"
        account.displayName shouldEqualTo "test"
        account.userName shouldEqualTo "test"
    }

    @Test(expected = MastodonException::class)
    fun getMutesWithException() {
        val client = MockClient.ioException()
        val mutes = Mutes(client)
        mutes.getMutes().execute()
    }
}
