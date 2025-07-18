package mastodon4j.api.entity.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Calckey/Misskey互換性ユーティリティ
 * 
 * CalckeyやMisskeyのID形式と標準のMastodon ID形式の相互変換や、
 * 時刻文字列の解析をマルチプラットフォーム対応で提供します。
 */
object CalckeyCompatUtil {

    /**
     * ID文字列をLong値に変換するか、Calckey ID形式からLong値に変換し、
     * 変換できない場合は時刻文字列から疑似的なIDを生成します。
     * 
     * @param id_ 変換対象のID文字列
     * @param timeString 代替として使用する時刻文字列（ISO 8601形式）
     * @return 変換されたLong値
     */
    fun toLongOrCalckeyIdOrFakeTimeId(id_: String, timeString: String): Long {
        return try {
            id_.toLong()
        } catch (e: NumberFormatException) {
            val calckeyId = aidToCalckeyId(id_)
            if (calckeyId == 0L) {
                parseDateStringAsEpochMillis(timeString) ?: Clock.System.now().toEpochMilliseconds()
            } else {
                calckeyId
            }
        }
    }

    /**
     * ISO 8601形式の時刻文字列をエポック時刻（ミリ秒）に変換します。
     * 
     * @param dateString ISO 8601形式の時刻文字列（例: "2023-01-01T12:00:00.000Z"）
     * @return エポック時刻（ミリ秒）、解析に失敗した場合はnull
     */
    fun parseDateStringAsEpochMillis(dateString: String): Long? {
        if (dateString.isEmpty()) {
            return null
        }

        return try {
            // kotlinx.datetimeでISO 8601形式の文字列を解析
            val instant = Instant.parse(dateString)
            instant.toEpochMilliseconds()
        } catch (e: Exception) {
            // フォールバック: 様々なフォーマットに対応
            parseFlexibleDateString(dateString)
        }
    }

    /**
     * 柔軟な日時文字列解析（フォールバック用）
     */
    private fun parseFlexibleDateString(dateString: String): Long? {
        return try {
            // マイクロ秒やナノ秒が含まれている場合の対応
            var normalizedString = dateString
            
            // タイムゾーンがない場合はUTCとして扱う
            if (!normalizedString.endsWith("Z") && !normalizedString.contains("+") && normalizedString.indexOf("-", 19) == -1) {
                normalizedString += "Z"
            }
            
            // マイクロ秒を削除（ISO 8601標準では最大3桁のミリ秒まで）
            val microsecondPattern = """(\.\d{3})\d+""".toRegex()
            normalizedString = microsecondPattern.replace(normalizedString, "$1")
            
            val instant = Instant.parse(normalizedString)
            instant.toEpochMilliseconds()
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Instant形式で時刻文字列を解析します。
     * 
     * @param dateString ISO 8601形式の時刻文字列
     * @return 解析されたInstant、失敗した場合はnull
     */
    fun parseDateString(dateString: String): Instant? {
        if (dateString.isEmpty()) {
            return null
        }

        return try {
            Instant.parse(dateString)
        } catch (e: Exception) {
            // フォールバック解析
            parseFlexibleDateString(dateString)?.let {
                Instant.fromEpochMilliseconds(it)
            }
        }
    }

//    /**
//     * 現在時刻を取得します。
//     *
//     * @return 現在のInstant
//     */
//    fun now(): Instant = Clock.System.now()
//
//    /**
//     * 現在時刻をエポック時刻（ミリ秒）で取得します。
//     *
//     * @return 現在のエポック時刻（ミリ秒）
//     */
//    fun currentTimeMillis(): Long = Clock.System.now().toEpochMilliseconds()

    /**
     * aid（MisskeyのID形式の一つ）をCalckey IDに変換します。
     * 
     * `aid`はMisskeyで使用されるID形式の一つです。
     * `calckey id`はCalckeyの一部のAPIで使用されるID形式です。
     * 
     * @param aid 変換対象のaid文字列
     * @return 変換されたCalckey ID（Long値）、変換に失敗した場合は0L
     */
    fun aidToCalckeyId(aid: String): Long {
        return aid.toLongOrNull(36) ?: 0L
    }

//    /**
//     * Instantを指定したタイムゾーンでの文字列表現に変換します。
//     *
//     * @param instant 変換対象のInstant
//     * @param timeZone タイムゾーン（デフォルトはUTC）
//     * @return ISO 8601形式の文字列
//     */
//    fun formatInstant(instant: Instant, timeZone: TimeZone = TimeZone.UTC): String {
//        return instant.toLocalDateTime(timeZone).toString()
//    }
}