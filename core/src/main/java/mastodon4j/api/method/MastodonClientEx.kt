package mastodon4j.api.method

import mastodon4j.MastodonClient

val MastodonClient.accounts get() = AccountsMethod(this)
val MastodonClient.apps get() = AppsMethod(this)
val MastodonClient.blocks get() = BlocksMethod(this)
val MastodonClient.bookmarks get() = BookmarksMethod(this)
val MastodonClient.favourites get() = FavouritesMethod(this)
val MastodonClient.followRequests get() = FollowRequestsMethod(this)
val MastodonClient.follows get() = FollowsMethod(this)
val MastodonClient.lists get() = ListsMethod(this)
val MastodonClient.media get() = MediaMethod(this)
val MastodonClient.mutes get() = MutesMethod(this)
val MastodonClient.notifications get() = NotificationsMethod(this)
val MastodonClient.public get() = PublicMethod(this)
val MastodonClient.reports get() = ReportsMethod(this)
val MastodonClient.search get() = SearchMethod(this)
val MastodonClient.statuses get() = StatusesMethod(this)
val MastodonClient.streaming get() = StreamingMethod(this)
val MastodonClient.timelines get() = TimelinesMethod(this)
val MastodonClient.trends get() = TrendsMethod(this)
