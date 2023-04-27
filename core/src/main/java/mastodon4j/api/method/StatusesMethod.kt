package mastodon4j.api.method

import mastodon4j.MastodonClient
import mastodon4j.MastodonRequest
import mastodon4j.Parameter
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.*
import mastodon4j.api.exception.MastodonException
import mastodon4j.extension.emptyRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#statuses
 */
class StatusesMethod(private val client: MastodonClient) {

    //  GET /api/v1/statuses/:id
    @Throws(MastodonException::class)
    fun getStatus(statusId: String): MastodonRequest<Status> {
        return MastodonRequest<Status>(
            {
                client.get("/api/v1/statuses/$statusId")
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        )
    }

    //  GET /api/v1/statuses/:id/context
    @Throws(MastodonException::class)
    fun getContext(statusId: String): MastodonRequest<Context> {
        return MastodonRequest<Context>(
            {
                client.get("/api/v1/statuses/$statusId/context")
            },
            {
                client.getSerializer().fromJson(it, Context::class.java)
            }
        )
    }

//    //  GET /api/v1/statuses/:id/card =>
//    //  2.6.0 - deprecated in favor of card property inlined on Status entity
//    @Throws(MastodonException::class)
//    fun getCard(statusId: String): MastodonRequest<Card> {
//        return MastodonRequest<Card>(
//            {
//                client.get("statuses/$statusId/card")
//            },
//            {
//                client.getSerializer().fromJson(it, Card::class.java)
//            }
//        )
//    }

    //  GET /api/v1/reblogged_by
    @JvmOverloads
    @Throws(MastodonException::class)
    fun getRebloggedBy(statusId: String, range: Range = Range()): MastodonRequest<Pageable<Account>> {
        return MastodonRequest<Pageable<Account>>(
            {
                client.get(
                    "/api/v1/statuses/$statusId/reblogged_by",
                    range.toParameter()
                )
            },
            {
                client.getSerializer().fromJson(it, Account::class.java)
            }
        ).toPageable()
    }

    //  GET /api/v1/favourited_by
    @JvmOverloads
    @Throws(MastodonException::class)
    fun getFavouritedBy(statusId: String, range: Range = Range()): MastodonRequest<Pageable<Account>> {
        return MastodonRequest<Pageable<Account>>(
            {
                client.get(
                    "/api/v1/statuses/$statusId/favourited_by",
                    range.toParameter()
                )
            },
            {
                client.getSerializer().fromJson(it, Account::class.java)
            }
        ).toPageable()
    }

    /**
     * POST /api/v1/statuses
     */
    @JvmOverloads
    @Throws(MastodonException::class)
    fun postStatus(
        status: String,
        inReplyToId: Long?,
        mediaIds: List<String>?,
        sensitive: Boolean,
        spoilerText: String?,
        visibility: Status.Visibility = Status.Visibility.Public,
        quoteId: String?
    ): MastodonRequest<Status> {
        val parameters = Parameter().apply {
            append("status", status)
            inReplyToId?.let {
                append("in_reply_to_id", it)
            }
            mediaIds?.let {
                append("media_ids", it)
            }
            append("sensitive", sensitive)
            spoilerText?.let {
                append("spoiler_text", it)
            }
            append("visibility", visibility.value)
            quoteId?.let {
                append("quote_id", it)
            }
        }.build()

        return MastodonRequest<Status>(
            {
                client.post(
                    "/api/v1/statuses",
                    parameters
                        .toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull())
                )
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        )
    }

    /**
     * PUT /api/v1/statuses/:id
     *
     * @see "https://docs.joinmastodon.org/methods/statuses/#edit"
     */
    @Throws(MastodonException::class)
    fun editStatus(
        statusId: String,
        status: String,
        spoilerText: String?,
        sensitive: Boolean,
        mediaIds: List<String>?,
    ): MastodonRequest<Status> {
        val parameters = Parameter().apply {
            append("status", status)
            mediaIds?.let {
                append("media_ids", it)
            }
            append("sensitive", sensitive)
            spoilerText?.let {
                append("spoiler_text", it)
            }
        }.build()

        return MastodonRequest(
            {
                client.put(
                    "/api/v1/statuses/$statusId",
                    parameters
                        .toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull())
                )
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        )
    }

    //  DELETE /api/v1/statuses/:id
    @Throws(MastodonException::class)
    fun deleteStatus(statusId: String) {
        val response = client.delete("/api/v1/statuses/$statusId")
        if (!response.isSuccessful) {
            throw MastodonException(response)
        }
    }

    //  POST /api/v1/statuses/:id/reblog
    @Throws(MastodonException::class)
    fun postReblog(statusId: String): MastodonRequest<Status> {
        return MastodonRequest<Status>(
            {
                client.post("/api/v1/statuses/$statusId/reblog", emptyRequestBody())
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        )
    }

    //  POST /api/v1/statuses/:id/unreblog
    @Throws(MastodonException::class)
    fun postUnreblog(statusId: String): MastodonRequest<Status> {
        return MastodonRequest<Status>(
            {
                client.post("/api/v1/statuses/$statusId/unreblog", emptyRequestBody())
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        )
    }

    //  POST /api/v1/statuses/:id/favourite
    @Throws(MastodonException::class)
    fun postFavourite(statusId: String): MastodonRequest<Status> {
        return MastodonRequest<Status>(
            {
                client.post("/api/v1/statuses/$statusId/favourite", emptyRequestBody())
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        )
    }

    //  POST /api/v1/statuses/:id/unfavourite
    @Throws(MastodonException::class)
    fun postUnfavourite(statusId: String): MastodonRequest<Status> {
        return MastodonRequest<Status>(
            {
                client.post("/api/v1/statuses/$statusId/unfavourite", emptyRequestBody())
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        )
    }

    // POST /api/v1/statuses/:id/pin
    @Throws(MastodonException::class)
    fun postPin(statusId: String): MastodonRequest<Status> {
        return MastodonRequest<Status>(
            {
                client.post("/api/v1/statuses/$statusId/pin", emptyRequestBody())
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        )
    }

    // POST /api/v1/statuses/:id/unpin
    @Throws(MastodonException::class)
    fun postUnpin(statusId: String): MastodonRequest<Status> {
        return MastodonRequest<Status>(
            {
                client.post("/api/v1/statuses/$statusId/unpin", emptyRequestBody())
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        )
    }

    // POST /api/v1/statuses/:id/bookmark
    @Throws(MastodonException::class)
    fun postBookmark(statusId: String): MastodonRequest<Status> {
        return MastodonRequest<Status>(
            {
                client.post("/api/v1/statuses/$statusId/bookmark", emptyRequestBody())
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        )
    }

    // POST /api/v1/statuses/:id/unbookmark
    @Throws(MastodonException::class)
    fun postUnbookmark(statusId: String): MastodonRequest<Status> {
        return MastodonRequest<Status>(
            {
                client.post("/api/v1/statuses/$statusId/unbookmark", emptyRequestBody())
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        )
    }

    // PUT /api/v1/statuses/:id/emoji_reactions/:emojiName
    @Throws(MastodonException::class)
    fun putEmojiReaction(statusId: String, emojiName: String) {
        val response = client.put("/api/v1/statuses/$statusId/emoji_reactions/$emojiName", emptyRequestBody())
        if (!response.isSuccessful) {
            throw MastodonException(response)
        }
    }

    // POST /api/v1/statuses/:id/emoji_unreactions
    fun deleteAllEmojiReactions(statusId: String) {
        val response = client.post("/api/v1/statuses/$statusId/emoji_unreactions", emptyRequestBody())
        if (!response.isSuccessful) {
            throw MastodonException(response)
        }
    }

    // DELETE /api/v1/statuses/:id/emoji_reaction/:emojiName
    fun deleteEmojiReaction(statusId: String, emojiName: String) {
        val response = client.delete("/api/v1/statuses/$statusId/emoji_reactions/$emojiName")
        if (!response.isSuccessful) {
            throw MastodonException(response)
        }
    }

    // GET /api/v1/statuses/:id/emoji_reactioned_by
    fun getEmojiReactionedByUsers(statusId: String): MastodonRequest<Pageable<EmojiReactionedAccount>> {
        return MastodonRequest<Pageable<EmojiReactionedAccount>>(
            {
                client.get(
                    "/api/v1/statuses/$statusId/emoji_reactioned_by"
                )
            },
            {
                client.getSerializer().fromJson(it, EmojiReactionedAccount::class.java)
            }
        ).toPageable()
    }

    // GET /api/v1/emoji_reactions
    @Throws(MastodonException::class)
    fun getEmojiReactions(range: Range): MastodonRequest<Pageable<Status>> {
        return MastodonRequest<Pageable<Status>>(
            {
                client.get("/api/v1/emoji_reactions", range.toParameter())
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        ).toPageable()
    }

    // POST /api/v1/polls/:id/votes
    @Throws(MastodonException::class)
    fun postPollsVotes(pollId: Long, choices: List<Int>): MastodonRequest<Poll> {
        val parameters = Parameter().apply {
            append("choices", choices)
        }.build()

        return MastodonRequest<Poll>(
            {
                client.post(
                    "/api/v1/polls/$pollId/votes",
                    parameters
                        .toRequestBody("application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull())
                )
            },
            {
                client.getSerializer().fromJson(it, Poll::class.java)
            }
        )
    }

}
