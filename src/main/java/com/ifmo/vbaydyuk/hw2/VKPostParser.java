package com.ifmo.vbaydyuk.hw2;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import javax.annotation.Nonnull;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * this class performs parsing json objects obtained from vk api server after newsfeed.search query
 */
public class VKPostParser {

    static final String RESPONSE = "response";
    static final String ITEMS = "items";
    static final String DATE = "date";
    static final String TEXT = "text";

    static final int MILLIS_PER_SECOND = 1000;
    static final int SECONDS_PER_HOUR = 3600;


    private static VKPost vkPostFromJSON(JsonObject jsonObject) {
        return new VKPost(jsonObject.getAsJsonPrimitive(TEXT).toString(),
                new Date(jsonObject.getAsJsonPrimitive(DATE).getAsLong() * MILLIS_PER_SECOND));
    }

    public static long hoursOldFormat(@Nonnull Date date) {
        long unixTime = date.getTime();
        return (System.currentTimeMillis() - unixTime) / (SECONDS_PER_HOUR * MILLIS_PER_SECOND);
    }

    @Nonnull
    public List<VKPost> getPosts(@Nonnull Reader reader) {
        List<VKPost> posts = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonObject responseJson = jsonParser.parse(new JsonReader(reader)).getAsJsonObject();
        JsonArray array = responseJson.getAsJsonObject(RESPONSE)
                .getAsJsonArray(ITEMS);

        array.forEach(o -> posts.add(vkPostFromJSON(o.getAsJsonObject())));

        return posts;
    }


}
