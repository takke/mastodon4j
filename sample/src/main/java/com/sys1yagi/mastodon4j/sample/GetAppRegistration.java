package com.sys1yagi.mastodon4j.sample;

import com.google.gson.Gson;
import com.sys1yagi.mastodon4j.MastodonClient;
import mastodon4j.api.Scope;
import mastodon4j.api.entity.auth.AppRegistration;
import mastodon4j.api.exception.MastodonException;
import mastodon4j.api.method.Apps;
import okhttp3.OkHttpClient;

public class GetAppRegistration {
    public static void main(String[] args) {
        MastodonClient client = new MastodonClient.Builder("mstdn.jp", new OkHttpClient.Builder(), new Gson()).build();
        Apps apps = new Apps(client);
        try {
            AppRegistration registration = apps.createApp(
                    "mastodon4j-sample-app",
                    "urn:ietf:wg:oauth:2.0:oob",
                    new Scope(Scope.Name.ALL),
                    ""
            ).execute();
            System.out.println("instance=" + registration.getInstanceName());
            System.out.println("client_id=" + registration.getClientId());
            System.out.println("client_secret=" + registration.getClientSecret());
        } catch (MastodonException e) {
            int statusCode = e.getResponse().code();
        }
    }
}
