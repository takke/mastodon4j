//package mastodon4j.api.method
//
//import mastodon4j.MastodonClient
//import mastodon4j.MastodonRequest
//import mastodon4j.Parameter
//import mastodon4j.api.Pageable
//import mastodon4j.api.Range
//import mastodon4j.api.entity.Account
//import mastodon4j.api.entity.CredentialAccount
//import mastodon4j.api.entity.MstList
//import mastodon4j.api.entity.Relationship
//import mastodon4j.api.entity.Status
//import mastodon4j.api.entity.Suggestion
//import mastodon4j.api.exception.MastodonException
//import mastodon4j.extension.emptyRequestBody
//import okhttp3.MediaType.Companion.toMediaType
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.RequestBody.Companion.asRequestBody
//import okhttp3.RequestBody.Companion.toRequestBody
//import java.io.File
//
///**
// * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#accounts
// */
//class AccountsMethod(private val client: MastodonClient) {
//    // GET /api/v1/accounts/:id
//    fun getAccount(accountId: String): MastodonRequest<Account> {
//        return MastodonRequest(
//            { client.get("/api/v1/accounts/$accountId") },
//            { client.getSerializer().fromJson(it, Account::class.java) }
//        )
//    }
//
//    // GET /api/v1/accounts/lookup
//    fun lookup(acct: String): MastodonRequest<Account> {
//        return MastodonRequest(
//            { client.get("/api/v1/accounts/lookup", Parameter().append("acct", acct)) },
//            { client.getSerializer().fromJson(it, Account::class.java) }
//        )
//    }
//
//    //  GET /api/v1/accounts/verify_credentials
//    fun getVerifyCredentials(): MastodonRequest<CredentialAccount> {
//        return MastodonRequest(
//            { client.get("/api/v1/accounts/verify_credentials") },
//            { client.getSerializer().fromJson(it, CredentialAccount::class.java) }
//        )
//    }
//
//    /**
//     * PATCH /api/v1/accounts/update_credentials
//     * display_name: The name to display in the user's profile
//     * note: A new biography for the user
//     */
//    fun updateCredential(
//        displayName: String?,
//        note: String?,
//        fieldsAttributesNames: List<String>? = null,
//        fieldsAttributesValues: List<String>? = null
//    ): MastodonRequest<Account> {
//        val parameters = Parameter().apply {
//            displayName?.let {
//                append("display_name", it)
//            }
//            note?.let {
//                append("note", it)
//            }
//
//            // fields_attributes
//            if (fieldsAttributesNames != null && fieldsAttributesValues != null) {
//
//                if (fieldsAttributesNames.size != fieldsAttributesValues.size) {
//                    throw MastodonException("fieldsAttributesNames.size != fieldsAttributesValues.size")
//                }
//
//                for (i in fieldsAttributesNames.indices) {
//                    append("fields_attributes[$i][name]", fieldsAttributesNames[i])
//                    append("fields_attributes[$i][value]", fieldsAttributesValues[i])
//                }
//            }
//        }.build()
//        return MastodonRequest(
//            {
//                client.patch(
//                    "/api/v1/accounts/update_credentials",
//                    parameters
//                        .toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull())
//                )
//            },
//            {
//                client.getSerializer().fromJson(it, Account::class.java)
//            }
//        )
//    }
//
//    fun updateAvatar(avatarFile: File, mimeType: String): MastodonRequest<Account> {
//        return MastodonRequest(
//            {
//                client.patch(
//                    "/api/v1/accounts/update_credentials",
//                    MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart(
//                            "avatar", avatarFile.name,
//                            avatarFile.asRequestBody(mimeType.toMediaType())
//                        )
//                        .build()
//                )
//            },
//            {
//                client.getSerializer().fromJson(it, Account::class.java)
//            }
//        )
//    }
//
//    fun updateHeader(headerFile: File, mimeType: String): MastodonRequest<Account> {
//        return MastodonRequest(
//            {
//                client.patch(
//                    "/api/v1/accounts/update_credentials",
//                    MultipartBody.Builder()
//                        .setType(MultipartBody.FORM)
//                        .addFormDataPart(
//                            "header", headerFile.name,
//                            headerFile.asRequestBody(mimeType.toMediaType())
//                        )
//                        .build()
//                )
//            },
//            {
//                client.getSerializer().fromJson(it, Account::class.java)
//            }
//        )
//    }
//
//    //  GET /api/v1/accounts/:id/followers
//    @JvmOverloads
//    fun getFollowers(accountId: String, range: Range = Range()): MastodonRequest<Pageable<Account>> {
//        return MastodonRequest<Pageable<Account>>(
//            {
//                client.get(
//                    "/api/v1/accounts/$accountId/followers",
//                    range.toParameter()
//                )
//            },
//            {
//                client.getSerializer().fromJson(it, Account::class.java)
//            }
//        ).toPageable()
//    }
//
//    //  GET /api/v1/accounts/:id/following
//    @JvmOverloads
//    fun getFollowing(accountId: String, range: Range = Range()): MastodonRequest<Pageable<Account>> {
//        return MastodonRequest<Pageable<Account>>(
//            {
//                client.get(
//                    "/api/v1/accounts/$accountId/following",
//                    range.toParameter()
//                )
//            },
//            {
//                client.getSerializer().fromJson(it, Account::class.java)
//            }
//        ).toPageable()
//    }
//
//    // GET /api/v2/suggestions
//    fun getSuggestions(): MastodonRequest<List<Suggestion>> {
//        return MastodonRequest(
//            {
//                client.get(
//                    "/api/v2/suggestions"
//                )
//            },
//            {
//                client.getSerializer().fromJson(it, Suggestion::class.java)
//            }
//        )
//    }
//
//    //  GET /api/v1/accounts/:id/statuses
//    @JvmOverloads
//    fun getStatuses(
//        accountId: String,
//        onlyMedia: Boolean = false,
//        excludeReplies: Boolean = false,
//        pinned: Boolean = false,
//        range: Range = Range()
//    ): MastodonRequest<Pageable<Status>> {
//        val parameters = range.toParameter()
//        if (onlyMedia) {
//            parameters.append("only_media", true)
//        }
//        if (pinned) {
//            parameters.append("pinned", true)
//        }
//        if (excludeReplies) {
//            parameters.append("exclude_replies", true)
//        }
//        return MastodonRequest<Pageable<Status>>(
//            {
//                client.get(
//                    "/api/v1/accounts/$accountId/statuses",
//                    parameters
//                )
//            },
//            {
//                client.getSerializer().fromJson(it, Status::class.java)
//            }
//        ).toPageable()
//    }
//
//    //  POST /api/v1/accounts/:id/follow
//    fun postFollow(accountId: String, reblogs: Boolean? = null, notify: Boolean? = null): MastodonRequest<Relationship> {
//        return MastodonRequest(
//            {
//                val parameter = Parameter().apply {
//                    if (reblogs != null) {
//                        append("reblogs", reblogs)
//                    }
//                    if (notify != null) {
//                        append("notify", notify)
//                    }
//                }.build()
//
//                client.post(
//                    "/api/v1/accounts/$accountId/follow",
//                    parameter.toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull())
//
//                )
//            },
//            {
//                client.getSerializer().fromJson(it, Relationship::class.java)
//            }
//        )
//    }
//
//    //  POST /api/v1/accounts/:id/unfollow
//    fun postUnFollow(accountId: String): MastodonRequest<Relationship> {
//        return MastodonRequest(
//            {
//                client.post("/api/v1/accounts/$accountId/unfollow", emptyRequestBody())
//            },
//            {
//                client.getSerializer().fromJson(it, Relationship::class.java)
//            }
//        )
//    }
//
//    //  POST /api/v1/accounts/:id/block
//    fun postBlock(accountId: String): MastodonRequest<Relationship> {
//        return MastodonRequest(
//            {
//                client.post("/api/v1/accounts/$accountId/block", emptyRequestBody())
//            },
//            {
//                client.getSerializer().fromJson(it, Relationship::class.java)
//            }
//        )
//    }
//
//    //  POST /api/v1/accounts/:id/unblock
//    fun postUnblock(accountId: String): MastodonRequest<Relationship> {
//        return MastodonRequest(
//            {
//                client.post("/api/v1/accounts/$accountId/unblock", emptyRequestBody())
//            },
//            {
//                client.getSerializer().fromJson(it, Relationship::class.java)
//            }
//        )
//    }
//
//    //  POST /api/v1/accounts/:id/mute
//    fun postMute(accountId: String): MastodonRequest<Relationship> {
//        return MastodonRequest(
//            {
//                client.post("/api/v1/accounts/$accountId/mute", emptyRequestBody())
//            },
//            {
//                client.getSerializer().fromJson(it, Relationship::class.java)
//            }
//        )
//    }
//
//    //  POST /api/v1/accounts/:id/unmute
//    fun postUnmute(accountId: String): MastodonRequest<Relationship> {
//        return MastodonRequest(
//            {
//                client.post("/api/v1/accounts/$accountId/unmute", emptyRequestBody())
//            },
//            {
//                client.getSerializer().fromJson(it, Relationship::class.java)
//            }
//        )
//    }
//
//    //  GET /api/v1/accounts/relationships
//    fun getRelationships(accountIds: List<String>): MastodonRequest<List<Relationship>> {
//        return MastodonRequest(
//            {
//                client.get(
//                    "/api/v1/accounts/relationships",
//                    Parameter().append("id", accountIds)
//                )
//            },
//            {
//                client.getSerializer().fromJson(it, Relationship::class.java)
//            }
//        )
//    }
//
//    // GET /api/v1/accounts/search
//    /**
//     * q: What to search for
//     * limit: Maximum number of matching accounts to return (default: 40)
//     */
//    @JvmOverloads
//    fun search(query: String, limit: Int = 40, resolve: Boolean = false): MastodonRequest<List<Account>> {
//        return MastodonRequest(
//            {
//                client.get(
//                    "/api/v1/accounts/search",
//                    Parameter().apply {
//                        append("q", query)
//                        append("limit", limit)
//                        if (resolve) {
//                            append("resolve", resolve)
//                        }
//                    }
//                )
//            },
//            {
//                client.getSerializer().fromJson(it, Account::class.java)
//            }
//        )
//    }
//
//    /**
//     * GET /api/v1/accounts/:id/lists
//     */
//    @Throws(MastodonException::class)
//    fun getListsContainingThisAccount(accountId: String): MastodonRequest<Pageable<MstList>> {
//        return MastodonRequest<Pageable<MstList>>(
//            {
//                client.get("/api/v1/accounts/${accountId}/lists")
//            },
//            {
//                client.getSerializer().fromJson(it, MstList::class.java)
//            }
//        ).toPageable()
//    }
//}
