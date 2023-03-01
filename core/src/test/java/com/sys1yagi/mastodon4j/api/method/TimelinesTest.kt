package com.sys1yagi.mastodon4j.api.method

import com.sys1yagi.mastodon4j.testtool.MockClient
import mastodon4j.api.exception.MastodonException
import org.amshove.kluent.shouldEqualTo
import org.junit.Test

class TimelinesTest {

    @Test
    fun getHome() {
        val client = MockClient.mock("timelines.json")
        val timelines = Timelines(client)
        val pageable = timelines.getHome().execute()
        val status = pageable.part.first()
        status.id shouldEqualTo 11111L
    }

    @Test(expected = MastodonException::class)
    fun homeWithException() {
        val client = MockClient.ioException()
        val timelines = Timelines(client)
        timelines.getHome().execute()
    }

    // TODO 401

}
