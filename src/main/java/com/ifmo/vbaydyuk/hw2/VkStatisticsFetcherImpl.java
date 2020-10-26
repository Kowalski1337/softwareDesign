package com.ifmo.vbaydyuk.hw2;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Simple implementation of {@link VkStatisticsFetcher}
 *
 * @author vbaydyuk
 * @since 19.10.2020
 */
public class VkStatisticsFetcherImpl implements VkStatisticsFetcher {

    private final VkApi vkApi;

    public VkStatisticsFetcherImpl(VkApi vkApi) {
        this.vkApi = vkApi;
    }

    @Nonnull
    @Override
    public Map<VkUser, Integer> getFriendsPostLikeStatistics(int userId, int lastPosts) {
        List<VkUser> friends = vkApi.getAllFriends(userId);
        if (friends == null) {
            return Collections.emptyMap();
        }
        return friends.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        vkUser -> {
                            List<Integer> allPost = vkApi.getAllPosts(vkUser.getId(), lastPosts);
                            if (allPost == null) return 0;
                            return (int) allPost
                                    .stream()
                                    .filter(postId -> vkApi.isLiked(userId, vkUser.getId(), postId))
                                    .count();
                        }
                ));
    }
}
