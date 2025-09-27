package mastodon4j.api.entity

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

object QuoteSerializer : KSerializer<Quote?> {
    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    override val descriptor: SerialDescriptor = JsonElement.serializer().descriptor

    override fun deserialize(decoder: Decoder): Quote? {
        val jsonElement = decoder.decodeSerializableValue(JsonElement.serializer())

        if (jsonElement is JsonNull) {
            return null
        }

        if (jsonElement !is JsonObject) {
            throw SerializationException("Expected JsonObject for Quote, got ${jsonElement::class.simpleName}")
        }

        // stateキーが存在するかチェック
        val hasState = jsonElement.containsKey("state")

        return if (hasState) {
            // Mastodon公式形式: { "state": "accepted", "quoted_status": {...} }
            val state = jsonElement["state"]?.jsonPrimitive?.content ?: "accepted"
            val quotedStatusJson = jsonElement["quoted_status"]
            val quotedStatus = when (quotedStatusJson) {
                null, is JsonNull -> null
                else -> json.decodeFromJsonElement<Status>(quotedStatusJson)
            }
            Quote(state = state, quotedStatus = quotedStatus)
        } else {
            // fedibird形式: 直接Statusオブジェクトを持つ
            // idキーが存在することでStatusオブジェクトであることを確認
            if (jsonElement.containsKey("id")) {
                val status = json.decodeFromJsonElement<Status>(jsonElement)
                Quote(state = "accepted", quotedStatus = status)
            } else {
                // 空のオブジェクトまたは不正な形式の場合
                null
            }
        }
    }

    override fun serialize(encoder: Encoder, value: Quote?) {
        if (value == null) {
            encoder.encodeSerializableValue(JsonNull.serializer(), JsonNull)
        } else {
            val jsonObject = buildJsonObject {
                put("state", JsonPrimitive(value.state))
                value.quotedStatus?.let {
                    put("quoted_status", json.encodeToJsonElement(it))
                }
            }
            encoder.encodeSerializableValue(JsonElement.serializer(), jsonObject)
        }
    }
}