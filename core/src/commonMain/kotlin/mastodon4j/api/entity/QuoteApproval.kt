package mastodon4j.api.entity

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

/**
 * 引用投稿の承認情報を表すエンティティ
 * see more https://docs.joinmastodon.org/entities/QuoteApproval/
 */
@Serializable(with = QuoteApprovalSerializer::class)
data class QuoteApproval(
    // 自動的に承認される引用を可能にする対象
    // "public", "followers", "following", "unsupported_policy" の値を含む
    // 注意: APIによっては単一の文字列として返される場合がある
    val automatic: List<String> = emptyList(),

    // 手動レビュー後に承認される引用を可能にする対象
    // "public", "followers", "following", "unsupported_policy" の値を含む
    // 注意: APIによっては単一の文字列として返される場合がある
    val manual: List<String> = emptyList(),

    // この投稿の引用ポリシーが現在のユーザーにどのように適用されるか
    // "automatic", "manual", "denied", "unknown" の値を取る
    val currentUser: String = "",
)

/**
 * QuoteApprovalのカスタムシリアライザ
 * automaticとmanualフィールドが文字列または配列のどちらでも受け入れる
 */
object QuoteApprovalSerializer : KSerializer<QuoteApproval> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("QuoteApproval") {
        element<List<String>>("automatic")
        element<List<String>>("manual")
        element<String>("current_user")
    }

    override fun deserialize(decoder: Decoder): QuoteApproval {
        val jsonDecoder = decoder as? JsonDecoder
            ?: return QuoteApproval()
        val jsonObject = jsonDecoder.decodeJsonElement().jsonObject

        val automatic = parseStringOrArray(jsonObject["automatic"])
        val manual = parseStringOrArray(jsonObject["manual"])
        val currentUser = jsonObject["current_user"]?.jsonPrimitive?.content ?: ""

        return QuoteApproval(
            automatic = automatic,
            manual = manual,
            currentUser = currentUser,
        )
    }

    override fun serialize(encoder: Encoder, value: QuoteApproval) {
        // シリアライズは配列形式で行う
        val surrogate = QuoteApprovalSurrogate(
            automatic = value.automatic,
            manual = value.manual,
            currentUser = value.currentUser,
        )
        encoder.encodeSerializableValue(QuoteApprovalSurrogate.serializer(), surrogate)
    }

    // 文字列または配列のどちらでもList<String>に変換する
    private fun parseStringOrArray(element: JsonElement?): List<String> {
        return when (element) {
            is JsonArray -> element.map { it.jsonPrimitive.content }
            is JsonPrimitive -> if (element.isString) listOf(element.content) else emptyList()
            else -> emptyList()
        }
    }
}

/**
 * シリアライズ用のサロゲートクラス
 */
@Serializable
private data class QuoteApprovalSurrogate(
    @SerialName("automatic") val automatic: List<String>,
    @SerialName("manual") val manual: List<String>,
    @SerialName("current_user") val currentUser: String,
)
