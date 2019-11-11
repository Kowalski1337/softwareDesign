package com.ifmo.vbaydyuk.hw2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public class VKPostFetcher {

    private static final Logger log = Logger.getLogger("VKPostFetcher");

    private static final String ACCESS_TOKEN = "bf4b7394912886b4f9494208cbeb5e3f78a709614e50a956afc57f7b96b730aa2b94b83541c022ea1cffd";
    private static final String QUERY = "https://api.vk.com/method/newsfeed.search?q=";
    private static final String VERSION_AND_TOKEN_KEY_WORD = "&v=5.52&access_token";

    @Nullable
    public Reader fetch(@Nonnull String searchText) {
        URL url;
        String query = QUERY + searchText + VERSION_AND_TOKEN_KEY_WORD + "=" + ACCESS_TOKEN;

        log.info("Crating url..");
        try {
            url = new URL(query);
        } catch (MalformedURLException e) {
            log.warning(String.format("Wrong url format: %s, reason: %s", query, e.getMessage()));
            return null;
        }
        URLConnection connection;

        log.info("Opening connection..");
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            log.warning(String.format("Cannot open connection for url: %s, reason: %s", query, e.getMessage()));
            return null;
        }

        InputStream inputStream;

        log.info("Getting request result..");
        try {
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            log.warning(String.format("Cannot get request result for url: %s, reason: %s", query, e.getMessage()));
            return null;
        }

        return new InputStreamReader(inputStream);
    }

    public static void main(String[] args) {
        VKPostFetcher fetcher = new VKPostFetcher();
        Reader result = fetcher.fetch("cheburek");
        //System.out.println(result);
    }

}
