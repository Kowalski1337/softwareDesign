package com.ifmo.vbaydyuk.hw2;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests {@link VkStatisticsFetcher}
 *
 * @author vbaydyuk
 * @since 19.10.2020
 */
class VkStatisticsFetcherTest {
    private final VkApi vkApi = mock(VkApi.class);
    private final VkStatisticsFetcher vkStatisticsFetcher = new VkStatisticsFetcherImpl(vkApi);
    private static final int LAST_POSTS = 3;
    private static final int DEFAULT_TEST_ID = 1337;
    private static final int FRIEND1 = 1;
    private static final int FRIEND2 = 2;
    private static final int FRIEND3 = 3;
    private static final int POST1 = 1;
    private static final int POST2 = 2;
    private static final int POST3 = 3;

    @Test
    public void testCannotGetFriends() {
        when(vkApi.getAllFriends(anyInt())).thenReturn(null);
        Map<VkUser, Integer> statistic = vkStatisticsFetcher.getFriendsPostLikeStatistics(DEFAULT_TEST_ID, LAST_POSTS);
        assertEquals(emptyMap(), statistic);
    }

    @Test
    public void testAllFriendsHavePrivateAccess() {
        Map<Integer, Integer> friendsToLikes = ImmutableMap.of(FRIEND1, 0, FRIEND2, 0, FRIEND3, 0);
        Set<Integer> friendsIds = getUsersIds(friendsToLikes);
        List<VkUser> friends = getUsers(friendsToLikes);
        Map<VkUser, Integer> expectedStatistics = getStatistics(friendsToLikes);
        when(vkApi.getAllFriends(DEFAULT_TEST_ID)).thenReturn(
                friends
        );
        friendsIds.forEach(
                id -> when(vkApi.getAllPosts(eq(id), anyInt())).thenReturn(null)
        );
        Map<VkUser, Integer> statistics = vkStatisticsFetcher.getFriendsPostLikeStatistics(DEFAULT_TEST_ID, LAST_POSTS);
        assertEquals(expectedStatistics, statistics);
    }

    @Test
    public void testAllLikes() {
        Map<Integer, Integer> friendsToLikes = ImmutableMap.of(FRIEND1, LAST_POSTS, FRIEND2, LAST_POSTS, FRIEND3, LAST_POSTS);
        Map<Integer, List<Integer>> friendsToPosts = ImmutableMap.of(
                FRIEND1, ImmutableList.of(POST1, POST2, POST3),
                FRIEND2, ImmutableList.of(POST1, POST2, POST3),
                FRIEND3, ImmutableList.of(POST1, POST2, POST3)
        );
        List<VkUser> friends = getUsers(friendsToLikes);
        Map<VkUser, Integer> expectedStatistics = getStatistics(friendsToLikes);
        when(vkApi.getAllFriends(DEFAULT_TEST_ID)).thenReturn(
                friends
        );
        friendsToPosts.forEach(
                (id, posts) -> when(vkApi.getAllPosts(id, LAST_POSTS)).thenReturn(posts)
        );
        friendsToPosts.forEach(
                (f, p) -> likeAllPosts(vkApi, f, p)
        );
        Map<VkUser, Integer> statistics = vkStatisticsFetcher.getFriendsPostLikeStatistics(DEFAULT_TEST_ID, LAST_POSTS);
        assertEquals(expectedStatistics, statistics);
    }

    @Test
    public void testNotAllLikes() {
        Map<Integer, Integer> friendsToLikes = ImmutableMap.of(FRIEND1, LAST_POSTS, FRIEND2, 1, FRIEND3, 2);
        Map<Integer, List<Integer>> friendsToPosts = ImmutableMap.of(
                FRIEND1, ImmutableList.of(POST1, POST2, POST3),
                FRIEND2, ImmutableList.of(POST1, POST2, POST3),
                FRIEND3, ImmutableList.of(POST1, POST2, POST3)
        );
        List<VkUser> friends = getUsers(friendsToLikes);
        Map<VkUser, Integer> expectedStatistics = getStatistics(friendsToLikes);
        when(vkApi.getAllFriends(DEFAULT_TEST_ID)).thenReturn(
                friends
        );
        friendsToPosts.forEach(
                (id, posts) -> when(vkApi.getAllPosts(id, LAST_POSTS)).thenReturn(posts)
        );
        likeAllPosts(vkApi, FRIEND1, friendsToPosts.get(FRIEND1));
        likeFirstNPosts(vkApi, FRIEND2, friendsToPosts.get(FRIEND2), 1);
        likeFirstNPosts(vkApi, FRIEND3, friendsToPosts.get(FRIEND3), 2);
        Map<VkUser, Integer> statistics = vkStatisticsFetcher.getFriendsPostLikeStatistics(DEFAULT_TEST_ID, LAST_POSTS);
        assertEquals(expectedStatistics, statistics);
    }

    @Test
    public void testOneFriendUnavailable() {
        Map<Integer, Integer> friendsToLikes = ImmutableMap.of(FRIEND1, LAST_POSTS, FRIEND2, 1, FRIEND3, 0);
        Map<Integer, List<Integer>> friendsToPosts = ImmutableMap.of(
                FRIEND1, ImmutableList.of(POST1, POST2, POST3),
                FRIEND2, ImmutableList.of(POST1, POST2, POST3),
                FRIEND3, ImmutableList.of(POST1, POST2, POST3)
        );
        List<VkUser> friends = getUsers(friendsToLikes);
        Map<VkUser, Integer> expectedStatistics = getStatistics(friendsToLikes);
        when(vkApi.getAllFriends(DEFAULT_TEST_ID)).thenReturn(
                friends
        );
        when(vkApi.getAllPosts(FRIEND1, LAST_POSTS)).thenReturn(friendsToPosts.get(FRIEND1));
        when(vkApi.getAllPosts(FRIEND2, LAST_POSTS)).thenReturn(friendsToPosts.get(FRIEND2));
        when(vkApi.getAllPosts(FRIEND3, LAST_POSTS)).thenReturn(null);
        likeAllPosts(vkApi, FRIEND1, friendsToPosts.get(FRIEND1));
        likeFirstNPosts(vkApi, FRIEND2, friendsToPosts.get(FRIEND2), 1);
        likeFirstNPosts(vkApi, FRIEND3, friendsToPosts.get(FRIEND3), 2);
        Map<VkUser, Integer> statistics = vkStatisticsFetcher.getFriendsPostLikeStatistics(DEFAULT_TEST_ID, LAST_POSTS);
        assertEquals(expectedStatistics, statistics);
    }

    private static void likeAllPosts(VkApi vkApi, int ownerId, List<Integer> posts) {
        likeFirstNPosts(vkApi, ownerId, posts, posts.size());
    }

    private static void likeFirstNPosts(VkApi vkApi, int ownerId, List<Integer> posts, int n) {
        for (int i = 0; i < posts.size(); i++) {
            when(vkApi.isLiked(DEFAULT_TEST_ID, ownerId, posts.get(i))).thenReturn(i < n);
        }
    }

    private static VkUser generateTestUser(int id) {
        return new VkUser(id, "Test" + id, "Testov" + id);
    }

    private static Set<Integer> getUsersIds(Map<Integer, Integer> usersToLikes) {
        return usersToLikes.keySet();
    }

    private static List<VkUser> getUsers(Map<Integer, Integer> usersToLikes) {
        return usersToLikes.keySet()
                .stream()
                .map(VkStatisticsFetcherTest::generateTestUser)
                .collect(Collectors.toList());
    }

    private static Map<VkUser, Integer> getStatistics(Map<Integer, Integer> usersToLikes) {
        return usersToLikes
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        e -> generateTestUser(e.getKey()),
                        Map.Entry::getValue
                ));
    }
}