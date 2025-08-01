package com.sys1yagi.mastodon4j.api.entity

import com.google.gson.Gson
import com.sys1yagi.mastodon4j.testtool.AssetsUtil
import mastodon4j.api.entity.MediaAttachment
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBe
import org.junit.Test

class AttachmentTest {
    @Test
    fun deserialize() {
        val json = AssetsUtil.readFromAssets("attachment.json")
        val status: MediaAttachment = Gson().fromJson(json, MediaAttachment::class.java)
        status.id shouldEqualTo 10L
        status.url shouldEqualTo "youtube"
        status.remoteUrl shouldNotBe null
        status.previewUrl shouldEqualTo "preview"
        status.textUrl shouldNotBe null
        status.type shouldEqualTo MediaAttachment.Type.Video.value
    }
}
