package mastodon4j.api.entity.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object CalckeyCompatUtil {

    // In calckey, id is a String, so convert time to id
    // TODO id to string
    fun toLongOrFakeTimeId(id_: String, timeString: String): Long {
        return try {
            id_.toLong()
        } catch (e: NumberFormatException) {
            parseDate(timeString)?.time ?: System.currentTimeMillis()
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

}
