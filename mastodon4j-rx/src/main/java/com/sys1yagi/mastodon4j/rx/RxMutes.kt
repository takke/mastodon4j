package com.sys1yagi.mastodon4j.rx

import com.sys1yagi.mastodon4j.MastodonClient
import mastodon4j.api.Pageable
import mastodon4j.api.Range
import mastodon4j.api.entity.Account
import mastodon4j.api.method.Mutes
import io.reactivex.Single

class RxMutes(client: MastodonClient) {
    val mutes = Mutes(client)

    fun getMutes(range: Range = Range()): Single<Pageable<Account>> {
        return Single.create {
            try {
                val accounts = mutes.getMutes(range)
                it.onSuccess(accounts.execute())
            } catch (e: Throwable) {
                it.onError(e)
            }
        }
    }
}