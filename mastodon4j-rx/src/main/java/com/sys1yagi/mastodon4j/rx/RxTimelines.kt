package com.sys1yagi.mastodon4j.rx

import com.sys1yagi.mastodon4j.MastodonClient
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Status
import mastodon4j.api.method.Timelines
import com.sys1yagi.mastodon4j.rx.extensions.onErrorIfNotDisposed
import com.sys1yagi.mastodon4j.rx.extensions.single
import io.reactivex.Single

class RxTimelines(client: MastodonClient) {
    val timelines = Timelines(client)

    fun getHome(range: Range = Range()): Single<Pageable<Status>> {
        return single {
            timelines.getHome(range).execute()
        }
    }
}
