package mastodon4j.api.method

import mastodon4j.MastodonClient

val MastodonClient.accounts get() = AccountsMethod(this)
val MastodonClient.apps get() = AppsMethod(this)
val MastodonClient.blocks get() = BlocksMethod(this)
val MastodonClient.favourites get() = FavouritesMethod(this)
val MastodonClient.followRequests get() = FollowRequestsMethod(this)
val MastodonClient.follows get() = FollowsMethod(this)
val MastodonClient.lists get() = ListsMethod(this)
val MastodonClient.media get() = MediaMethod(this)
val MastodonClient.mutes get() = MutesMethod(this)
val MastodonClient.notifications get() = Notifications(this)
val MastodonClient.public get() = Public(this)
val MastodonClient.reports get() = Reports(this)
val MastodonClient.search get() = SearchMethod(this)
val MastodonClient.statuses get() = Statuses(this)
val MastodonClient.streaming get() = Streaming(this)
val MastodonClient.timelines get() = Timelines(this)
val MastodonClient.trends get() = Trends(this)
