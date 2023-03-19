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
import okhttp3.RequestBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#statuses
 */
class StatusesMethod(private val client: MastodonClient) {

    //  GET /api/v1/statuses/:id
    @Throws(MastodonException::class)
    fun getStatus(statusId: Long): MastodonRequest<Status> {
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
    fun getContext(statusId: Long): MastodonRequest<Context> {
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
//    fun getCard(statusId: Long): MastodonRequest<Card> {
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
    fun getRebloggedBy(statusId: Long, range: Range = Range()): MastodonRequest<Pageable<Account>> {
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
    fun getFavouritedBy(statusId: Long, range: Range = Range()): MastodonRequest<Pageable<Account>> {
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
     * POST /api/v1/status
     * status: The text of the status
     * in_reply_to_id (optional): local ID of the status you want to reply to
     * media_ids (optional): array of media IDs to attach to the status (maximum 4)
     * sensitive (optional): set this to mark the media of the status as NSFW
     * spoiler_text (optional): text to be shown as a warning before the actual content
     * visibility (optional): either "direct", "private", "unlisted" or "public"
     */
    @JvmOverloads
    @Throws(MastodonException::class)
    fun postStatus(
        status: String,
        inReplyToId: Long?,
        mediaIds: List<Long>?,
        sensitive: Boolean,
        spoilerText: String?,
        visibility: Status.Visibility = Status.Visibility.Public
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
        }.build()

        return MastodonRequest<Status>(
            {
                client.post(
                    "/api/v1/statuses",
                    RequestBody.create(
                        "application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull(),
                        parameters
                    )
                )
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        )
    }

    //  DELETE /api/v1/statuses/:id
    @Throws(MastodonException::class)
    fun deleteStatus(statusId: Long) {
        val response = client.delete("/api/v1/statuses/$statusId")
        if (!response.isSuccessful) {
            throw MastodonException(response)
        }
    }

    //  POST /api/v1/statuses/:id/reblog
    @Throws(MastodonException::class)
    fun postReblog(statusId: Long): MastodonRequest<Status> {
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
    fun postUnreblog(statusId: Long): MastodonRequest<Status> {
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
    fun postFavourite(statusId: Long): MastodonRequest<Status> {
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
    fun postUnfavourite(statusId: Long): MastodonRequest<Status> {
        return MastodonRequest<Status>(
            {
                client.post("/api/v1/statuses/$statusId/unfavourite", emptyRequestBody())
            },
            {
                client.getSerializer().fromJson(it, Status::class.java)
            }
        )
    }

    // POST /api/v1/statuses/:id/bookmark
    @Throws(MastodonException::class)
    fun postBookmark(statusId: Long): MastodonRequest<Status> {
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
    fun postUnbookmark(statusId: Long): MastodonRequest<Status> {
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
    fun putEmojiReaction(statusId: Long, emojiName: String) {
        val response = client.put("/api/v1/statuses/$statusId/emoji_reactions/$emojiName", emptyRequestBody())
        if (!response.isSuccessful) {
            throw MastodonException(response)
        }
    }

    // POST /api/v1/statuses/:id/emoji_unreactions
    fun deleteAllEmojiReactions(statusId: Long) {
        val response = client.post("/api/v1/statuses/$statusId/emoji_unreactions", emptyRequestBody())
        if (!response.isSuccessful) {
            throw MastodonException(response)
        }
    }

    // DELETE /api/v1/statuses/:id/emoji_reaction/:emojiName
    fun deleteEmojiReaction(statusId: Long, emojiName: String) {
        val response = client.delete("/api/v1/statuses/$statusId/emoji_reactions/$emojiName")
        if (!response.isSuccessful) {
            throw MastodonException(response)
        }
    }

    // GET /api/v1/statuses/:id/emoji_reactioned_by
    fun getEmojiReactionedByUsers(statusId: Long): MastodonRequest<Pageable<EmojiReactionedAccount>> {
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
                    RequestBody.create(
                        "application/x-www-form-urlencoded; charset=utf-8".toMediaTypeOrNull(),
                        parameters
                    )
                )
            },
            {
                client.getSerializer().fromJson(it, Poll::class.java)
            }
        )
    }

}
