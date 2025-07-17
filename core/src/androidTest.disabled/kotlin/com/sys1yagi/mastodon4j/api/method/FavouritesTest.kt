package com.sys1yagi.mastodon4j.api.method

import com.sys1yagi.mastodon4j.testtool.MockClient
import mastodon4j.api.exception.MastodonException
import org.amshove.kluent.shouldEqualTo
import org.junit.Assert.*
import org.junit.Test

class FavouritesTest {
    @Test
    fun getFavourites() {
        val client = MockClient.mock("favourites.json")

        val favorites = Favourites(client)
        val pageable = favorites.getFavourites().execute()
        val status = pageable.part.first()
        status.id shouldEqualTo 1111
    }

    @Test(expected = MastodonException::class)
    fun exception() {
        val client = MockClient.ioException()

        val favorites = Favourites(client)
        favorites.getFavourites().execute()
    }
}
