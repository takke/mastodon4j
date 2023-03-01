package com.sys1yagi.mastodon4j.sample;

import com.google.gson.Gson;
import com.sys1yagi.mastodon4j.MastodonClient;
import mastodon4j.api.Pageable;
import mastodon4j.api.Range;
import mastodon4j.api.entity.Status;
import mastodon4j.api.exception.MastodonException;
import mastodon4j.api.method.Public;
import mastodon4j.api.method.Timelines;
import okhttp3.OkHttpClient;

import java.util.List;

public class GetTagTimelines {
    public static void main(String[] args) {
        MastodonClient client = new MastodonClient.Builder("mstdn.jp", new OkHttpClient.Builder(), new Gson()).build();
        Public publicMethod = new Public(client);

        try {
            Pageable<Status> statuses = publicMethod.getFederatedTag("mastodon", new Range()).execute();
            statuses.getPart().forEach(status -> {
                System.out.println("=============");
                System.out.println(status.getAccount().getDisplayName());
                System.out.println(status.getContent());
                System.out.println(status.isReblogged());
            });
        } catch (MastodonException e) {
            e.printStackTrace();
        }
    }
}
