package mastodon4j.api.entity.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object CalckeyCompatUtilLegacy {

    // In calckey, id is a String, so convert to calckey id, or convert time to id
    fun toLongOrCalckeyIdOrFakeTimeId(id_: String, timeString: String): Long {
        return try {
            id_.toLong()
        } catch (e: NumberFormatException) {
            val calckeyId = aidToCalckeyId(id_)
            if (calckeyId == 0L) {
                parseDate(timeString)?.time ?: System.currentTimeMillis()
            } else {
                calckeyId
            }
        }
    }

//    fun toLongOrNull(id_: String?): Long? {
//        return try {
//            id_?.toLong()
//        } catch (e: NumberFormatException) {
//            null
//        }
//    }

    private val sdf by lazy {
        val format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        SimpleDateFormat(format, Locale.US).also {
            it.timeZone = TimeZone.getTimeZone("UTC")
        }
    }

    fun parseDate(dateString: String): Date? {
        if (dateString.isEmpty()) {
            return null
        }

        return try {
            sdf.parse(dateString)
        } catch (e: ParseException) {
            null
        }
    }

    /**
     * aid to calckey id
     *
     * `aid` is one of the ids used in Misskey.
     * `calckey id` is the id for some apis in Calckey.
     */
    fun aidToCalckeyId(aid: String): Long {
        return aid.toLongOrNull(36) ?: 0L
    }

}
