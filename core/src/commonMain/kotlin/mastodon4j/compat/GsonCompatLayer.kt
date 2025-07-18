package mastodon4j.compat

import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

/**
 * Gson互換レイヤー
 * 
 * 既存のGsonを使用しているコードとの互換性を保つため、
 * kotlinx.serializationをGson風のAPIでラップ
 */
class GsonCompatLayer(private val json: Json) {
    
    /**
     * JSONをオブジェクトに変換（Gson互換）
     */
    fun <T> fromJson(jsonString: String, clazz: Class<T>): T {
        // 簡易的な型変換 - 実際の実装では型に応じた適切な処理が必要
        @Suppress("UNCHECKED_CAST")
        return when (clazz.simpleName) {
            "Status" -> json.decodeFromString<mastodon4j.api.entity.Status>(jsonString) as T
            "Account" -> json.decodeFromString<mastodon4j.api.entity.Account>(jsonString) as T
            "Context" -> json.decodeFromString<mastodon4j.api.entity.Context>(jsonString) as T
            "Application" -> json.decodeFromString<mastodon4j.api.entity.Application>(jsonString) as T
            "Notification" -> json.decodeFromString<mastodon4j.api.entity.Notification>(jsonString) as T
            "Relationship" -> json.decodeFromString<mastodon4j.api.entity.Relationship>(jsonString) as T
            "CredentialAccount" -> json.decodeFromString<mastodon4j.api.entity.CredentialAccount>(jsonString) as T
            "Announcement" -> json.decodeFromString<mastodon4j.api.entity.Announcement>(jsonString) as T
            else -> throw IllegalArgumentException("Unsupported type: ${clazz.simpleName}")
        }
    }

    /**
     * オブジェクトをJSONに変換（Gson互換）
     */
    fun <T> toJson(obj: T): String {
        @Suppress("UNCHECKED_CAST")
        return when (obj) {
            is mastodon4j.api.entity.Status -> json.encodeToString(obj)
            is mastodon4j.api.entity.Account -> json.encodeToString(obj)
            is mastodon4j.api.entity.Context -> json.encodeToString(obj)
            is mastodon4j.api.entity.Application -> json.encodeToString(obj)
            is mastodon4j.api.entity.Notification -> json.encodeToString(obj)
            is mastodon4j.api.entity.Relationship -> json.encodeToString(obj)
            is mastodon4j.api.entity.CredentialAccount -> json.encodeToString(obj)
            is mastodon4j.api.entity.Announcement -> json.encodeToString(obj)
            else -> throw IllegalArgumentException("Unsupported type: ${obj!!::class.simpleName}")
        }
    }
}