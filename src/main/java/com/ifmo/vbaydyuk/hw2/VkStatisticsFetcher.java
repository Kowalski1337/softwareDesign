package com.ifmo.vbaydyuk.hw2;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Fetches vk statistics
 *
 * @author vbaydyuk
 * @since 19.10.2020
 */
public interface VkStatisticsFetcher {
    /**
     * Fetches how many posts did user with specified {@code userId} like per each his friend
     *
     * @param userId    user id to fetch statistics about
     * @param lastPosts how many last post should be included in statistics
     * @return mapping from friends to the number of liked posts
     */
    @Nonnull
    Map<VkUser, Integer> getFriendsPostLikeStatistics(int userId, int lastPosts);
}
