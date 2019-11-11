package com.ifmo.vbaydyuk.hw2;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Reader;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class SubstringFrequencyCounter {
    private static final Logger log = Logger.getLogger("SubstringFrequencyCounter");

    private final VKPostFetcher postFetcher;
    private final VKPostParser postParser;

    public SubstringFrequencyCounter(@Nonnull VKPostFetcher fetcher, @Nonnull VKPostParser parser) {
        this.postFetcher = Objects.requireNonNull(fetcher, "fetcher");
        this.postParser = Objects.requireNonNull(parser, "parser");
    }

    @Nullable
    public int[] run(String substr, int hours) {
        log.info("Fetching request response..");
        Reader response = postFetcher.fetch(substr);
        if (response == null) {
            log.warning("Cannot get response");
            return null;
        }
        List<VKPost> posts = postParser.getPosts(response);
        List<VKPost> relevantPosts = getRelevantPosts(posts, hours);
        return countStatistics(relevantPosts, hours);
    }

    private static List<VKPost> getRelevantPosts(List<VKPost> posts, int maxHoursOld) {
        return posts.stream()
                .filter(post -> VKPostParser.hoursOldFormat(post.getDate()) <= maxHoursOld)
                .collect(Collectors.toList());
    }

    private int[] countStatistics(List<VKPost> posts, int maxOldHours) {
        int[] statistics = new int[maxOldHours];
        posts.forEach(post -> statistics[(int) VKPostParser.hoursOldFormat(post.getDate())]++);
        return statistics;
    }

}
