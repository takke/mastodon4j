# mastodon4j

[![](https://jitpack.io/v/sys1yagi/mastodon4j.svg)](https://jitpack.io/#sys1yagi/mastodon4j)

mastodon4j is a [Mastodon](https://github.com/mastodon/mastodon) client library for Kotlin.

## Features

- Kotlin Multiplatform対応
- Ktor Client + Kotlin Serializationベース
- Coroutines対応（suspend関数）

## Official API Doc

https://docs.joinmastodon.org/api/

# Get Started

Mastodon4jはJitpackで公開されています。
(このtakke変更版は公開されていません)

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}
```

```kotlin
// build.gradle.kts
dependencies {
    implementation("com.github.sys1yagi.mastodon4j:mastodon4j:$version")
}
```

# Usage

## Basic Client Creation

```kotlin
val client = MastodonClient.Builder("mstdn.jp").build()
```

## Get Public Timeline

```kotlin
val client = MastodonClient.Builder("mstdn.jp").build()

// コルーチンスコープ内で実行
val statuses: List<Status> = client.timelines.getPublic().execute()
statuses.forEach { status ->
    println("=============")
    println(status.account?.displayName)
    println(status.content)
}
```

## Register App

認証が必要なAPIにアクセスするには、クライアント資格情報を作成してアクセストークンを取得する必要があります。詳細は[Mastodon API docs](https://docs.joinmastodon.org/client/token/)を参照してください。

```kotlin
val client = MastodonClient.Builder("mstdn.jp").build()

val appRegistration = client.apps.createApp(
    clientName = "my-app",
    redirectUris = "urn:ietf:wg:oauth:2.0:oob",
    scopes = Scope(Scope.Name.ALL),
    website = "https://example.com"
).execute()

// appRegistrationを保存してください（client_id, client_secretが含まれています）
save(appRegistration)
```

## OAuth Login and Get Access Token

```kotlin
val client = MastodonClient.Builder("mstdn.jp").build()
val clientId = appRegistration.clientId

// OAuthログインURLを生成
val url = client.apps.getOAuthUrl(clientId, Scope(Scope.Name.ALL))
// URLを開いてOAuthログインを行い、認可コードを取得
// https://:instance_name/oauth/authorize?client_id=:client_id&redirect_uri=:redirect_uri&response_type=code&scope=read

val authCode = // ユーザーが取得した認可コード
val clientSecret = appRegistration.clientSecret
val redirectUri = appRegistration.redirectUri

val accessToken = client.apps.getAccessToken(
    clientId = clientId,
    clientSecret = clientSecret,
    redirectUri = redirectUri,
    code = authCode,
    grantType = "authorization_code"
).execute()

// accessTokenを保存してください
```

## Get Home Timeline

```kotlin
// アクセストークンを設定してクライアントを作成
val client = MastodonClient.Builder("mstdn.jp")
    .accessToken(accessToken)
    .build()

val statuses: List<Status> = client.timelines.getHome().execute()
```

## Post a Status

```kotlin
val client = MastodonClient.Builder("mstdn.jp")
    .accessToken(accessToken)
    .build()

val status = client.statuses.postStatus(
    status = "Hello, Mastodon!",
    visibility = Status.Visibility.Public
).execute()
```

## Get Raw JSON

```kotlin
val client = MastodonClient.Builder("mstdn.jp").build()

client.public.getLocalPublic()
    .doOnJson { jsonString ->
        // 各要素のJSONを取得できます
        println(jsonString)
    }
    .execute()
```

## Timeout Configuration

デフォルトのタイムアウトは60秒に設定されています。メディアアップロードなど長時間かかる処理では、`httpClientConfig`パラメータでタイムアウトを延長できます。

```kotlin
import io.ktor.client.plugins.*

val client = MastodonClient.Builder(
    instanceName = "mstdn.jp",
    httpClientConfig = {
        // タイムアウトを3分に延長
        install(HttpTimeout) {
            requestTimeoutMillis = 180_000
            connectTimeoutMillis = 60_000
            socketTimeoutMillis = 180_000
        }
    }
)
    .accessToken(accessToken)
    .build()
```

| パラメータ | 説明 | デフォルト値 |
|-----------|------|-------------|
| `requestTimeoutMillis` | リクエスト全体のタイムアウト | 60,000ms |
| `connectTimeoutMillis` | 接続確立のタイムアウト | 60,000ms |
| `socketTimeoutMillis` | ソケット読み書きのタイムアウト | 60,000ms |

## Custom JSON Configuration

```kotlin
import kotlinx.serialization.json.Json

val customJson = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    isLenient = true
    prettyPrint = true
}

val client = MastodonClient.Builder(
    instanceName = "mstdn.jp",
    json = customJson
).build()
```

# Versioning

[Semantic Versioning 2.0.0](http://semver.org/spec/v2.0.0.html)

# Implementation Progress

## Methods

- [x] GET `/api/v1/accounts/:id`
- [x] GET `/api/v1/accounts/verify_credentials`
- [x] PATCH `/api/v1/accounts/update_credentials`
- [x] GET `/api/v1/accounts/:id/followers`
- [x] GET `/api/v1/accounts/:id/following`
- [x] GET `/api/v1/accounts/:id/statuses`
- [x] POST `/api/v1/accounts/:id/follow`
- [x] POST `/api/v1/accounts/:id/unfollow`
- [x] POST `/api/v1/accounts/:id/block`
- [x] POST `/api/v1/accounts/:id/unblock`
- [x] POST `/api/v1/accounts/:id/mute`
- [x] POST `/api/v1/accounts/:id/unmute`
- [x] GET `/api/v1/accounts/relationships`
- [x] GET `/api/v1/accounts/search`
- [x] POST `/api/v1/apps`
- [x] GET `/api/v1/blocks`
- [x] GET `/api/v1/favourites`
- [x] GET `/api/v1/follow_requests`
- [x] POST `/api/v1/follow_requests/:id/authorize`
- [x] POST `/api/v1/follow_requests/:id/reject`
- [x] POST `/api/v1/follows`
- [x] GET `/api/v1/instance`
- [x] POST `/api/v1/media`
- [x] GET `/api/v1/mutes`
- [x] GET `/api/v1/notifications`
- [x] GET `/api/v1/notifications/:id`
- [x] POST `/api/v1/notifications/clear`
- [x] GET `/api/v1/reports`
- [x] POST `/api/v1/reports`
- [x] GET `/api/v1/search`
- [x] GET `/api/v1/statuses/:id`
- [x] GET `/api/v1/statuses/:id/context`
- [x] GET `/api/v1/statuses/:id/card`
- [x] GET `/api/v1/statuses/:id/reblogged_by`
- [x] GET `/api/v1/statuses/:id/favourited_by`
- [x] POST `/api/v1/statuses`
- [x] DELETE `/api/v1/statuses/:id`
- [x] POST `/api/v1/statuses/:id/reblog`
- [x] POST `/api/v1/statuses/:id/unreblog`
- [x] POST `/api/v1/statuses/:id/favourite`
- [x] POST `/api/v1/statuses/:id/unfavourite`
- [x] GET `/api/v1/timelines/home`
- [x] GET `/api/v1/timelines/public`
- [x] GET `/api/v1/timelines/tag/:hashtag`

## Auth

- [x] Generate URL for OAuth `/oauth/authorize`
- [x] POST password authorize `/oauth/token`
- [x] POST `/oauth/token`

# Contribution

## Reporting Issues

問題を発見した場合や新機能のリクエストがある場合は、まず[issues](https://github.com/sys1yagi/mastodon4j/issues)で既存の報告を確認してください。未報告の場合は、明確で具体的なissueを作成してください。

# License

```
MIT License

Copyright (c) 2017 Toshihiro Yagi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
