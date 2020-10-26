package com.ifmo.vbaydyuk.hw2;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Connects to VkApi via OAuth 2.0 protocol
 *
 * @author vbaydyuk
 * @since 18.10.2020
 */
public interface VkApi {

    /**
     * Gets last {@code count} posts of user with specified {@code userId}
     *
     * @param userId user id
     * @param count  amount of post to be found
     * @return list of post ids, {@code null} if access is private
     */
    @Nullable
    List<Integer> getAllPosts(int userId, int count);

    /**
     * Gets all friends of user with specified {@code userId}
     *
     * @param userId user id
     * @return list of {@link VkUser}, {@code null} if access is private
     */
    @Nullable
    List<VkUser> getAllFriends(int userId);

    /**
     * Check if user with specified {@code userId} liked post with specified {@code postId}
     * of user with specified {@code ownerId}
     *
     * @param userId  user id
     * @param ownerId owner id
     * @param postId  post id
     * @return {@code true} if liked, {@code false} otherwise
     */
    boolean isLiked(int userId, int ownerId, int postId);
}
