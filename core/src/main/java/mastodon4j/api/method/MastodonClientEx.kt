package mastodon4j.api.method

import mastodon4j.MastodonClient

val MastodonClient.accounts get() = Accounts(this)
val MastodonClient.apps get() = Apps(this)
val MastodonClient.blocks get() = Blocks(this)
val MastodonClient.favourites get() = Favourites(this)
val MastodonClient.followRequests get() = FollowRequests(this)
val MastodonClient.follows get() = Follows(this)
val MastodonClient.mastodonLists get() = MastodonLists(this)
val MastodonClient.media get() = Media(this)
val MastodonClient.mutes get() = Mutes(this)
val MastodonClient.notifications get() = Notifications(this)
val MastodonClient.public get() = Public(this)
val MastodonClient.reports get() = Reports(this)
val MastodonClient.statuses get() = Statuses(this)
val MastodonClient.streaming get() = Streaming(this)
val MastodonClient.timelines get() = Timelines(this)
val MastodonClient.trends get() = Trends(this)