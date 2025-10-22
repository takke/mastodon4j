package mastodon4j.api.entity

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class InstanceTest {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Test
    fun parseInstanceV1WithStringThumbnail() {
        // /api/v1/instance のレスポンス（thumbnailが文字列形式）
        val jsonV1 = """
        {
          "uri": "mastodon.social",
          "title": "Mastodon",
          "short_description": "The original server operated by the Mastodon gGmbH non-profit",
          "description": "",
          "email": "staff@mastodon.social",
          "version": "3.5.3",
          "urls": {
            "streaming_api": "wss://mastodon.social"
          },
          "stats": {
            "user_count": 812303,
            "status_count": 38151616,
            "domain_count": 25255
          },
          "thumbnail": "https://files.mastodon.social/site_uploads/files/000/000/001/original/vlcsnap-2018-08-27-16h43m11s127.png",
          "languages": ["en"],
          "registrations": false,
          "approval_required": false,
          "invites_enabled": true
        }
        """.trimIndent()

        // JSONをパース
        val instance: Instance = json.decodeFromString(jsonV1)

        // 基本的なフィールドの検証
        assertEquals("mastodon.social", instance.uri)
        assertEquals("Mastodon", instance.title)
        assertEquals("The original server operated by the Mastodon gGmbH non-profit", instance.shortDescription)
        assertEquals("staff@mastodon.social", instance.email)
        assertEquals("3.5.3", instance.version)

        // stats の検証
        assertNotNull(instance.stats)
        assertEquals(812303L, instance.stats?.userCount)
        assertEquals(38151616L, instance.stats?.statusCount)
        assertEquals(25255L, instance.stats?.domainCount)

        // thumbnail（文字列形式）の検証
        assertNotNull(instance.thumbnail, "thumbnailフィールドがnullではないことを確認")
        assertEquals(
            "https://files.mastodon.social/site_uploads/files/000/000/001/original/vlcsnap-2018-08-27-16h43m11s127.png",
            instance.thumbnail?.url,
            "文字列形式のthumbnailが正しくパースされていることを確認"
        )
    }

    @Test
    fun parseInstanceV2WithObjectThumbnail() {
        // /api/v2/instance のレスポンス（thumbnailがオブジェクト形式）
        val jsonV2 = """
        {
          "domain": "mastodon.social",
          "title": "Mastodon",
          "version": "4.5.0-nightly.2025-07-11",
          "source_url": "https://github.com/mastodon/mastodon",
          "description": "The original server operated by the Mastodon gGmbH non-profit",
          "usage": {
            "users": {
              "active_month": 279347
            }
          },
          "thumbnail": {
            "url": "https://files.mastodon.social/site_uploads/files/000/000/001/@1x/57c12f441d083cde.png",
            "blurhash": "UeKUpFxuo~R%0nW;WCnhF6RjaJt757oJodS$",
            "versions": {
              "@1x": "https://files.mastodon.social/site_uploads/files/000/000/001/@1x/57c12f441d083cde.png",
              "@2x": "https://files.mastodon.social/site_uploads/files/000/000/001/@2x/57c12f441d083cde.png"
            }
          },
          "languages": [
            "en"
          ]
        }
        """.trimIndent()

        // JSONをパース
        val instance: Instance = json.decodeFromString(jsonV2)

        // 基本的なフィールドの検証
        assertEquals("mastodon.social", instance.domain, "domainが正しくパースされていることを確認")
        assertEquals("Mastodon", instance.title)
        assertEquals("The original server operated by the Mastodon gGmbH non-profit", instance.description)
        assertEquals("4.5.0-nightly.2025-07-11", instance.version)

        // thumbnail（オブジェクト形式）の検証
        assertNotNull(instance.thumbnail, "thumbnailフィールドがnullではないことを確認")
        assertEquals(
            "https://files.mastodon.social/site_uploads/files/000/000/001/@1x/57c12f441d083cde.png",
            instance.thumbnail?.url,
            "オブジェクト形式のthumbnailのurlが正しくパースされていることを確認"
        )
        assertEquals(
            "UeKUpFxuo~R%0nW;WCnhF6RjaJt757oJodS$",
            instance.thumbnail?.blurhash,
            "blurhashが正しくパースされていることを確認"
        )

        // versions の検証
        assertNotNull(instance.thumbnail?.versions, "versionsフィールドがnullではないことを確認")
        assertEquals(
            "https://files.mastodon.social/site_uploads/files/000/000/001/@1x/57c12f441d083cde.png",
            instance.thumbnail?.versions?.at1x,
            "@1xバージョンが正しくパースされていることを確認"
        )
        assertEquals(
            "https://files.mastodon.social/site_uploads/files/000/000/001/@2x/57c12f441d083cde.png",
            instance.thumbnail?.versions?.at2x,
            "@2xバージョンが正しくパースされていることを確認"
        )
    }
}
