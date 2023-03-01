package mastodon4j.extension

import com.google.gson.Gson
import mastodon4j.api.exception.MastodonException
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.Type

inline fun <reified T> Response.fromJson(gson: Gson, clazz: Class<T>): T {
    try {
        val json = body?.string() ?: throw MastodonException(Exception("response is null"))
        return gson.fromJson(json, clazz)
    } catch (e: IOException) {
        throw MastodonException(e)
    }
}

inline fun <reified T> Response.fromJson(gson: Gson, type: Type): T {
    try {
        val json = body?.string() ?: throw MastodonException(Exception("response is null"))
        return gson.fromJson(json, type)
    } catch (e: IOException) {
        throw MastodonException(e)
    }
}
