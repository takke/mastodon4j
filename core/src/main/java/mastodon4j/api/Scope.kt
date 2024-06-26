package mastodon4j.api

/**
 * see more https://github.com/tootsuite/documentation/blob/master/Using-the-API/OAuth-details.md
 */
class Scope
@JvmOverloads
constructor(private vararg val scopes: Name = arrayOf(Name.ALL)) {
    enum class Name(val scopeName: String) {
        READ("read"),
        WRITE("write"),
        FOLLOW("follow"),
        PUSH("push"),
        ALL(Scope(READ, WRITE, FOLLOW, PUSH).toString())
    }

    fun validate() {
        if (scopes.size != scopes.distinct().size) {
            throw IllegalArgumentException("There is a duplicate scope. : $this")
        }
    }

    override fun toString(): String {
        return scopes.joinToString(
            // "+" is url encoded " ".
            separator = "+",
            transform = { it.scopeName }
        )
    }
}
