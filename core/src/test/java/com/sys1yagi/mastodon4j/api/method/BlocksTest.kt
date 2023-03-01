package com.sys1yagi.mastodon4j.api.method

import com.sys1yagi.mastodon4j.testtool.MockClient
import mastodon4j.api.exception.MastodonException
import org.amshove.kluent.shouldEqualTo
import org.junit.Test

class BlocksTest {
    @Test
    fun getBlocks() {
        val client = MockClient.mock("blocks.json")

        val blocks = Blocks(client)
        val pageable = blocks.getBlocks().execute()
        val block = pageable.part.first()
        block.acct shouldEqualTo "test@test.com"
        block.displayName shouldEqualTo "test"
        block.userName shouldEqualTo "test"
    }

    @Test(expected = MastodonException::class)
    fun getBlocksException() {
        val client = MockClient.ioException()

        val blocks = Blocks(client)
        blocks.getBlocks().execute()
    }
}