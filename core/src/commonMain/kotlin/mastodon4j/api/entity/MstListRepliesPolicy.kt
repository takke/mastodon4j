package mastodon4j.api.entity

import kotlinx.serialization.Serializable

@Serializable
enum class MstListRepliesPolicy(val value: String) {

    Followed("followed"),
    List("list"),
    None("none"),
    ;

    companion object {
        fun fromString(value: String?): MstListRepliesPolicy {
            return values().firstOrNull { it.value == value } ?: List
        }
    }
}
