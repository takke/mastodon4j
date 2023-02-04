package com.sys1yagi.mastodon4j.api.method

import com.sys1yagi.mastodon4j.MastodonClient
import com.sys1yagi.mastodon4j.MastodonRequest
import com.sys1yagi.mastodon4j.api.entity.MediaAttachment
import okhttp3.MultipartBody

/**
 * See more https://github.com/tootsuite/documentation/blob/master/Using-the-API/API.md#media
 */
class Media(private val client: MastodonClient) {
    //  POST /api/v1/media
    fun postMedia(file: MultipartBody.Part): MastodonRequest<MediaAttachment> {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addPart(file)
            .build()
        return MastodonRequest<MediaAttachment>(
            {
                client.post("media", requestBody)
            },
            {
                client.getSerializer().fromJson(it, MediaAttachment::class.java)
            }
        )
    }
}
