package mastodon4j.api.entity

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Instant

class FilterTest {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    @Test
    fun parseStatusWithFiltered() {
        // Status.filtered を含むJSON (FilterResult 内の filter は keywords/statuses が省略される)
        val jsonString = """
        {
            "id": "103270115826048975",
            "created_at": "2019-12-08T03:48:33.901Z",
            "content": "<p>ネタバレを含む投稿</p>",
            "filtered": [
                {
                    "filter": {
                        "id": "3",
                        "title": "ネタバレ",
                        "context": ["home", "public", "unknown_context"],
                        "expires_at": "2022-09-20T17:27:39.296Z",
                        "filter_action": "warn"
                    },
                    "keyword_matches": ["ネタバレ"],
                    "status_matches": null
                },
                {
                    "filter": {
                        "id": "4",
                        "title": "非表示",
                        "context": ["notifications", "thread", "account"],
                        "expires_at": null,
                        "filter_action": "hide"
                    },
                    "keyword_matches": null,
                    "status_matches": ["103270115826048975"]
                }
            ]
        }
        """.trimIndent()

        val status: Status = json.decodeFromString(jsonString)

        assertEquals(2, status.filtered.size)

        val warn = status.filtered[0]
        assertEquals("3", warn.filter.id)
        assertEquals("ネタバレ", warn.filter.title)
        assertEquals(Filter.Action.Warn, warn.filter.filterAction)
        // 未知の context は除外される
        assertEquals(setOf(Filter.Context.Home, Filter.Context.Public), warn.filter.contexts)
        assertEquals(listOf("ネタバレ"), warn.keywordMatches)
        assertNull(warn.statusMatches)
        assertTrue(warn.filter.keywords.isEmpty())

        val hide = status.filtered[1]
        assertEquals(Filter.Action.Hide, hide.filter.filterAction)
        assertEquals(
            setOf(Filter.Context.Notifications, Filter.Context.Thread, Filter.Context.Account),
            hide.filter.contexts
        )
        assertNull(hide.keywordMatches)
        assertEquals(listOf("103270115826048975"), hide.statusMatches)
        assertNull(hide.filter.expiresAt)
    }

    @Test
    fun parseStatusWithoutFiltered() {
        // filtered が無い (フィルター非対応サーバー等) 場合は空リスト
        val status: Status = json.decodeFromString("""{"id": "1"}""")
        assertTrue(status.filtered.isEmpty())
    }

    @Test
    fun parseFilterWithKeywordsAndStatuses() {
        // GET /api/v2/filters の要素
        val jsonString = """
        {
            "id": "19972",
            "title": "Test filter",
            "context": ["home"],
            "expires_at": "2022-09-20T17:27:39.296Z",
            "filter_action": "blur",
            "keywords": [
                {"id": "1197", "keyword": "bad word", "whole_word": true}
            ],
            "statuses": [
                {"id": "1", "status_id": "109031743575371913"}
            ]
        }
        """.trimIndent()

        val filter: Filter = json.decodeFromString(jsonString)

        assertEquals(Filter.Action.Blur, filter.filterAction)
        assertEquals(1, filter.keywords.size)
        assertEquals("bad word", filter.keywords[0].keyword)
        assertTrue(filter.keywords[0].wholeWord)
        assertEquals(1, filter.statuses.size)
        assertEquals("109031743575371913", filter.statuses[0].statusId)
    }

    @Test
    fun unknownFilterActionIsTreatedAsWarn() {
        val filter: Filter = json.decodeFromString("""{"id": "1", "filter_action": "future_action"}""")
        assertEquals(Filter.Action.Warn, filter.filterAction)
        // 生の値は保持される
        assertEquals("future_action", filter.filterActionValue)
    }

    @Test
    fun isExpired() {
        val filter = Filter(expiresAt = "2022-09-20T17:27:39.296Z")
        assertFalse(filter.isExpired(Instant.parse("2022-09-20T17:27:39.000Z")))
        assertTrue(filter.isExpired(Instant.parse("2022-09-20T17:27:39.296Z")))
        assertTrue(filter.isExpired(Instant.parse("2022-09-21T00:00:00.000Z")))

        // 無期限
        assertFalse(Filter(expiresAt = null).isExpired(Instant.parse("2100-01-01T00:00:00.000Z")))
    }

    @Test
    fun filteredSurvivesReserialization() {
        // ストリーミング通知は再シリアライズしてDB保存されるため、encode/decode で filtered が保持されること
        val original = Status(
            id = "1",
            filtered = listOf(
                FilterResult(
                    filter = Filter(id = "3", title = "ネタバレ", contextValues = listOf("home"), filterActionValue = "hide"),
                    keywordMatches = listOf("ネタバレ"),
                )
            )
        )

        val decoded: Status = json.decodeFromString(json.encodeToString(Status.serializer(), original))

        assertEquals(original.filtered, decoded.filtered)
        assertEquals(Filter.Action.Hide, decoded.filtered[0].filter.filterAction)
    }
}
