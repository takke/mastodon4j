package com.sys1yagi.mastodon4j.rx

import com.sys1yagi.mastodon4j.MastodonClient
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Status
import mastodon4j.api.method.Favourites
import com.sys1yagi.mastodon4j.rx.extensions.onErrorIfNotDisposed
import io.reactivex.Single

class RxFavourites(client: MastodonClient) {
    val favourites = Favourites(client)

    fun getFavourites(range: Range = Range()): Single<Pageable<Status>> {
        return Single.create {
            try {
                val statuses = favourites.getFavourites(range)
                it.onSuccess(statuses.execute())
            } catch(throwable: Throwable) {
                it.onErrorIfNotDisposed(throwable)
            }
        }
    }
}