package com.sys1yagi.mastodon4j.sample;

import com.google.gson.Gson;
import com.sys1yagi.mastodon4j.MastodonClient;
import com.sys1yagi.mastodon4j.api.Range;
import com.sys1yagi.mastodon4j.api.entity.Status;
import com.sys1yagi.mastodon4j.api.exception.Mastodon4jRequestException;
import com.sys1yagi.mastodon4j.api.method.Timelines;
import okhttp3.OkHttpClient;

import java.util.List;

public class GetPublicTimelines {
    public static void main(String[] args) {
        MastodonClient client = new MastodonClient("mstdn.jp", new OkHttpClient(), new Gson());
        Timelines timelines = new Timelines(client);

        try {
            List<Status> statuses = timelines.getPublic(new Range());
            statuses.forEach(status -> {
                System.out.println("=============");
                System.out.println(status.getAccount().getDisplayName());
                System.out.println(status.getContent());
                System.out.println(status.isReblogged());
            });
        } catch (Mastodon4jRequestException e) {
            e.printStackTrace();
        }
    }
}