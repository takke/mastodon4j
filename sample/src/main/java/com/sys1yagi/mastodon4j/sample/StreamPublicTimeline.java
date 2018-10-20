package com.sys1yagi.mastodon4j.sample;

import com.google.gson.Gson;
import com.sys1yagi.mastodon4j.MastodonClient;
import mastodon4j.api.Handler;
import mastodon4j.api.Shutdownable;
import mastodon4j.api.entity.Notification;
import mastodon4j.api.entity.Status;
import mastodon4j.api.exception.MastodonException;
import mastodon4j.api.method.Streaming;
import okhttp3.OkHttpClient;

public class StreamPublicTimeline {
    public static void main(String[] args) {
        // require authentication even if public streaming
        String accessToken = "PUT YOUR ACCESS TOKEN";
        MastodonClient client = new MastodonClient.Builder("mstdn.jp", new OkHttpClient.Builder(), new Gson())
                .accessToken(accessToken)
                .useStreamingApi()
                .build();
        Handler handler = new Handler() {
            @Override
            public void onStatus(@NotNull Status status) {
                System.out.println(status.getContent());
            }

            @Override
            public void onNotification(@NotNull Notification notification) {

            }

            @Override
            public void onDelete(long id) {

            }

            @Override
            public void onDisconnected(@NotNull Retryable retryable) {

            }
        };
        Streaming streaming = new Streaming(client);
        try {
            Shutdownable shutdownable = streaming.localPublic(handler);
            Thread.sleep(10000L);
            shutdownable.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
