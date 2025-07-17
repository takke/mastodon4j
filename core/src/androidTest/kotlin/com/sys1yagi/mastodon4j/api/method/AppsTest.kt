package com.sys1yagi.mastodon4j.api.method

import com.sys1yagi.kmockito.invoked
import com.sys1yagi.kmockito.mock
import com.sys1yagi.mastodon4j.testtool.MockClient
import mastodon4j.MastodonClient
import mastodon4j.api.Scope
import mastodon4j.api.exception.MastodonException
import org.amshove.kluent.shouldEqualTo
import org.junit.Test

class AppsTest {
    @Test
    fun createApp() {
        val client: MastodonClient = MockClient.mock("app_registration.json")
        client.getInstanceName().invoked.thenReturn("mastodon.cloud")

        val apps = Apps(client)
        val registration = apps.createApp(
                clientName = "mastodon-android-sys1yagi",
                scope = Scope(Scope.Name.ALL)
        ).execute()

        registration.instanceName shouldEqualTo "mastodon.cloud"
        registration.clientId shouldEqualTo "client id"
        registration.clientSecret shouldEqualTo "client secret"
        registration.redirectUri shouldEqualTo "urn:ietf:wg:oauth:2.0:oob"
    }

    @Test(expected = MastodonException::class)
    fun createAppWithException() {
        val client = MockClient.ioException()

        val apps = Apps(client)
        apps.createApp(
                clientName = "mastodon-android-sys1yagi",
                scope = Scope(Scope.Name.ALL)
        ).execute()
    }

    @Test
    fun getOAuthUrl() {
        val client: MastodonClient = mock()
        client.getInstanceName().invoked.thenReturn("mastodon.cloud")

        val url = Apps(client).getOAuthUrl("client_id", Scope(Scope.Name.ALL))
        url shouldEqualTo "https://mastodon.cloud/oauth/authorize?client_id=client_id&redirect_uri=urn:ietf:wg:oauth:2.0:oob&response_type=code&scope=read write follow"
    }

    @Test
    fun getAccessToken() {
        val client: MastodonClient = MockClient.mock("access_token.json")
        val apps = Apps(client)
        val accessToken = apps.getAccessToken("test", "test", code = "test").execute()
        accessToken.accessToken shouldEqualTo "test"
        accessToken.scope shouldEqualTo "read write follow"
        accessToken.tokenType shouldEqualTo "bearer"
        accessToken.createdAt shouldEqualTo 1493188835
    }

    @Test(expected = MastodonException::class)
    fun getAccessTokenWithException() {
        val client: MastodonClient = MockClient.ioException()
        val apps = Apps(client)
        apps.getAccessToken("test", "test", code = "test").execute()
    }

    @Test
    fun postUserNameAndPassword() {
        val client: MastodonClient = MockClient.mock("access_token.json")
        val apps = Apps(client)
        val accessToken = apps.postUserNameAndPassword("test", "test", Scope(Scope.Name.ALL), "test", "test").execute()
        accessToken.accessToken shouldEqualTo "test"
        accessToken.scope shouldEqualTo "read write follow"
        accessToken.tokenType shouldEqualTo "bearer"
        accessToken.createdAt shouldEqualTo 1493188835
    }

    @Test(expected = MastodonException::class)
    fun postUserNameAndPasswordWithException() {
        val client: MastodonClient = MockClient.ioException()
        val apps = Apps(client)
        apps.postUserNameAndPassword("test", "test", Scope(Scope.Name.ALL), "test", "test").execute()
    }
}
