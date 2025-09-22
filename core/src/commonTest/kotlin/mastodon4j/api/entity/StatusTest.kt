package mastodon4j.api.entity

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class StatusTest {

    @Test
    fun parseStatusWithQuote() {
        // fedibird.comのquoteフィールドを含むJSONをパース
        val jsonWithQuote = """
        {
            "id": "123456789",
            "created_at": "2024-01-01T00:00:00.000Z",
            "in_reply_to_id": null,
            "in_reply_to_account_id": null,
            "sensitive": false,
            "spoiler_text": "",
            "visibility": "public",
            "language": "ja",
            "uri": "https://fedibird.com/@user/123456789",
            "url": "https://fedibird.com/@user/123456789",
            "replies_count": 0,
            "reblogs_count": 0,
            "favourites_count": 0,
            "edited_at": null,
            "favourited": false,
            "reblogged": false,
            "muted": false,
            "bookmarked": false,
            "pinned": false,
            "content": "<p>これは引用投稿のテストです</p>",
            "reblog": null,
            "application": null,
            "account": {
                "id": "1",
                "username": "testuser",
                "acct": "testuser",
                "display_name": "Test User",
                "locked": false,
                "bot": false,
                "discoverable": true,
                "group": false,
                "created_at": "2023-01-01T00:00:00.000Z",
                "note": "",
                "url": "https://fedibird.com/@testuser",
                "avatar": "https://example.com/avatar.png",
                "avatar_static": "https://example.com/avatar.png",
                "header": "https://example.com/header.png",
                "header_static": "https://example.com/header.png",
                "followers_count": 100,
                "following_count": 50,
                "statuses_count": 500,
                "last_status_at": "2024-01-01",
                "emojis": [],
                "fields": []
            },
            "media_attachments": [],
            "mentions": [],
            "tags": [],
            "emojis": [],
            "card": null,
            "poll": null,
            "quote": {
                "id": "987654321",
                "created_at": "2023-12-31T00:00:00.000Z",
                "in_reply_to_id": null,
                "in_reply_to_account_id": null,
                "sensitive": false,
                "spoiler_text": "",
                "visibility": "public",
                "language": "ja",
                "uri": "https://fedibird.com/@another/987654321",
                "url": "https://fedibird.com/@another/987654321",
                "replies_count": 5,
                "reblogs_count": 10,
                "favourites_count": 20,
                "edited_at": null,
                "favourited": false,
                "reblogged": false,
                "muted": false,
                "bookmarked": false,
                "pinned": false,
                "content": "<p>これは引用される元の投稿です</p>",
                "reblog": null,
                "application": null,
                "account": {
                    "id": "2",
                    "username": "anotheruser",
                    "acct": "anotheruser",
                    "display_name": "Another User",
                    "locked": false,
                    "bot": false,
                    "discoverable": true,
                    "group": false,
                    "created_at": "2023-01-01T00:00:00.000Z",
                    "note": "",
                    "url": "https://fedibird.com/@anotheruser",
                    "avatar": "https://example.com/avatar2.png",
                    "avatar_static": "https://example.com/avatar2.png",
                    "header": "https://example.com/header2.png",
                    "header_static": "https://example.com/header2.png",
                    "followers_count": 200,
                    "following_count": 100,
                    "statuses_count": 1000,
                    "last_status_at": "2023-12-31",
                    "emojis": [],
                    "fields": []
                },
                "media_attachments": [],
                "mentions": [],
                "tags": [],
                "emojis": [],
                "card": null,
                "poll": null
            }
        }
        """.trimIndent()

        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        // JSONをパース
        val status: Status = json.decodeFromString(jsonWithQuote)

        // 基本的なフィールドの検証
        assertEquals("123456789", status.id)
        val content1: String = status.content.replace("<p>", "").replace("</p>", "")
        assertEquals("これは引用投稿のテストです", content1)
        assertEquals("public", status.visibilityValue)

        // quoteフィールドが正しくパースされていることを確認
        assertNotNull(status.quote, "quoteフィールドがnullではないことを確認")
        status.quote?.let { quotedStatus ->
            assertEquals("987654321", quotedStatus.id)
            val content2: String = quotedStatus.content.replace("<p>", "").replace("</p>", "")
            assertEquals("これは引用される元の投稿です", content2)
            assertEquals("public", quotedStatus.visibilityValue)

            // 引用元の投稿のアカウント情報も確認
            assertNotNull(quotedStatus.account)
            assertEquals("anotheruser", quotedStatus.account?.userName)
            assertEquals("Another User", quotedStatus.account?.displayName)
        }
    }

    @Test
    fun parseStatusWithoutQuote() {
        // quoteフィールドを含まない通常のJSONをパース
        val jsonWithoutQuote = """
        {
            "id": "111111111",
            "created_at": "2024-01-01T00:00:00.000Z",
            "in_reply_to_id": null,
            "in_reply_to_account_id": null,
            "sensitive": false,
            "spoiler_text": "",
            "visibility": "public",
            "language": "ja",
            "uri": "https://mastodon.social/@user/111111111",
            "url": "https://mastodon.social/@user/111111111",
            "replies_count": 0,
            "reblogs_count": 0,
            "favourites_count": 0,
            "edited_at": null,
            "favourited": false,
            "reblogged": false,
            "muted": false,
            "bookmarked": false,
            "pinned": false,
            "content": "<p>通常の投稿です</p>",
            "reblog": null,
            "application": null,
            "account": {
                "id": "3",
                "username": "normaluser",
                "acct": "normaluser",
                "display_name": "Normal User",
                "locked": false,
                "bot": false,
                "discoverable": true,
                "group": false,
                "created_at": "2023-01-01T00:00:00.000Z",
                "note": "",
                "url": "https://mastodon.social/@normaluser",
                "avatar": "https://example.com/avatar3.png",
                "avatar_static": "https://example.com/avatar3.png",
                "header": "https://example.com/header3.png",
                "header_static": "https://example.com/header3.png",
                "followers_count": 50,
                "following_count": 30,
                "statuses_count": 100,
                "last_status_at": "2024-01-01",
                "emojis": [],
                "fields": []
            },
            "media_attachments": [],
            "mentions": [],
            "tags": [],
            "emojis": [],
            "card": null,
            "poll": null
        }
        """.trimIndent()

        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        // JSONをパース
        val status: Status = json.decodeFromString(jsonWithoutQuote)

        // 基本的なフィールドの検証
        assertEquals("111111111", status.id)
        val content3: String = status.content.replace("<p>", "").replace("</p>", "")
        assertEquals("通常の投稿です", content3)

        // quoteフィールドがnullであることを確認
        assertEquals(null, status.quote, "quoteフィールドが存在しない場合はnullであることを確認")
    }

    @Test
    fun parseStatusWithNullQuote() {
        // quoteフィールドが明示的にnullのJSONをパース
        val jsonWithNullQuote = """
        {
            "id": "222222222",
            "created_at": "2024-01-01T00:00:00.000Z",
            "in_reply_to_id": null,
            "in_reply_to_account_id": null,
            "sensitive": false,
            "spoiler_text": "",
            "visibility": "public",
            "language": "ja",
            "uri": "https://fedibird.com/@user/222222222",
            "url": "https://fedibird.com/@user/222222222",
            "replies_count": 0,
            "reblogs_count": 0,
            "favourites_count": 0,
            "edited_at": null,
            "favourited": false,
            "reblogged": false,
            "muted": false,
            "bookmarked": false,
            "pinned": false,
            "content": "<p>quoteがnullの投稿です</p>",
            "reblog": null,
            "quote": null,
            "application": null,
            "account": {
                "id": "4",
                "username": "testuser2",
                "acct": "testuser2",
                "display_name": "Test User 2",
                "locked": false,
                "bot": false,
                "discoverable": true,
                "group": false,
                "created_at": "2023-01-01T00:00:00.000Z",
                "note": "",
                "url": "https://fedibird.com/@testuser2",
                "avatar": "https://example.com/avatar4.png",
                "avatar_static": "https://example.com/avatar4.png",
                "header": "https://example.com/header4.png",
                "header_static": "https://example.com/header4.png",
                "followers_count": 75,
                "following_count": 40,
                "statuses_count": 200,
                "last_status_at": "2024-01-01",
                "emojis": [],
                "fields": []
            },
            "media_attachments": [],
            "mentions": [],
            "tags": [],
            "emojis": [],
            "card": null,
            "poll": null
        }
        """.trimIndent()

        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        // JSONをパース
        val status: Status = json.decodeFromString(jsonWithNullQuote)

        // 基本的なフィールドの検証
        assertEquals("222222222", status.id)
        val content4: String = status.content.replace("<p>", "").replace("</p>", "")
        assertEquals("quoteがnullの投稿です", content4)

        // quoteフィールドがnullであることを確認
        assertEquals(null, status.quote, "quoteフィールドが明示的にnullの場合もnullであることを確認")
    }
}