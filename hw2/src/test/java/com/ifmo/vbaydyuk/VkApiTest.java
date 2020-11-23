package com.ifmo.vbaydyuk;

import com.ifmo.vbaydyuk.response.IsLikedResponseWithError;
import com.ifmo.vbaydyuk.response.AllFriendsResponseWithError;
import com.ifmo.vbaydyuk.response.AllPostsResponseWithError;
import com.ifmo.vbaydyuk.response.error.Error;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.ifmo.vbaydyuk.response.error.VkApiErrors.PROFILE_IS_PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests {@link VkApi}
 *
 * @author vbaydyuk
 * @since 19.10.2020
 */
public class VkApiTest {
    private static final int PORT = 8080;
    private VkApiSimulator vkApiSimulator;
    private final VkApi vkApi = new VkApiImpl("http://localhost:" + PORT);
    private static final AllFriendsResponseWithError.User USER1 =
            new AllFriendsResponseWithError.User(1, null, "Primer", "Primerov");
    private static final AllFriendsResponseWithError.User USER2 =
            new AllFriendsResponseWithError.User(2, null, "Test", "Testov");
    private static final AllFriendsResponseWithError.User DEACTIVATED_USER =
            new AllFriendsResponseWithError.User(3, "banned", "Daria", "Ban");
    private static final VkUser VK_USER1 = new VkUser(1, "Primer", "Primerov");
    private static final VkUser VK_USER2 = new VkUser(2, "Test", "Testov");
    public static final String PROFILE_IS_PRIVATE_MSG = "Profile is private";
    private static final Error ERROR = new Error(
            PROFILE_IS_PRIVATE,
            PROFILE_IS_PRIVATE_MSG
    );
    private static final AllPostsResponseWithError.Post POST1 = new AllPostsResponseWithError.Post(1);
    private static final AllPostsResponseWithError.Post POST2 = new AllPostsResponseWithError.Post(2);
    private static final AllPostsResponseWithError.Post POST3 = new AllPostsResponseWithError.Post(3);
    private static final int LUCAS = 1;
    private static final int NE_LUCAS = 0;
    private static final int USER_ID = 1337;
    private static final int POST_COUNT = 3;
    private static final int FRIEND_COUNT = 2;

    @Before
    public void setUp() {
        vkApiSimulator = new VkApiSimulator(PORT);
        vkApiSimulator.start();
    }

    @After
    public void tearDown() {
        vkApiSimulator.stop();
    }

    @Test
    public void testGetAllFriends() {
        vkApiSimulator.successResponseForUrl(VkApiImpl.GET_FRIENDS_METHOD,
                new AllFriendsResponseWithError(
                        new AllFriendsResponseWithError.Response(
                                FRIEND_COUNT,
                                Arrays.asList(USER1, USER2)),
                        null
                )
        );
        List<VkUser> vkUsers = vkApi.getAllFriends(USER_ID);
        assertThat(vkUsers, hasSize(FRIEND_COUNT));
        assertThat(vkUsers, containsInAnyOrder(VK_USER1, VK_USER2));
    }

    @Test
    public void testGetAllFriendsWithDeactivated() {
        vkApiSimulator.successResponseForUrl(VkApiImpl.GET_FRIENDS_METHOD,
                new AllFriendsResponseWithError(
                        new AllFriendsResponseWithError.Response(
                                FRIEND_COUNT,
                                Arrays.asList(USER1, DEACTIVATED_USER)),
                        null
                )
        );
        List<VkUser> vkUsers = vkApi.getAllFriends(USER_ID);
        assertThat(vkUsers, hasSize(FRIEND_COUNT - 1));
        assertThat(vkUsers, containsInAnyOrder(VK_USER1));
    }

    @Test
    public void testAllFriendsError() {
        vkApiSimulator.successResponseForUrl(VkApiImpl.GET_FRIENDS_METHOD,
                new AllFriendsResponseWithError(
                        null,
                        ERROR
                ));
        List<VkUser> vkUsers = vkApi.getAllFriends(USER_ID);
        assertNull(vkUsers);
    }

    @Test
    public void testGetAllPosts() {
        vkApiSimulator.successResponseForUrl(VkApiImpl.WALL_GET_METHOD,
                new AllPostsResponseWithError(
                        new AllPostsResponseWithError.Response(
                                POST_COUNT,
                                Arrays.asList(POST1, POST2, POST3)
                        ),
                        null
                )
        );
        List<Integer> allPosts = vkApi.getAllPosts(USER_ID, POST_COUNT);
        assertThat(allPosts, hasSize(POST_COUNT));
        assertThat(allPosts, containsInAnyOrder(POST1.id, POST2.id, POST3.id));
    }

    @Test
    public void testGetAllPostsError() {
        vkApiSimulator.successResponseForUrl(VkApiImpl.WALL_GET_METHOD,
                new AllPostsResponseWithError(
                        null,
                        ERROR
                )
        );
        List<Integer> allPosts = vkApi.getAllPosts(USER_ID, POST_COUNT);
        assertNull(allPosts);
    }

    @Test
    public void testLiked() {
        vkApiSimulator.successResponseForUrl(VkApiImpl.IS_LIKED_METHOD,
                new IsLikedResponseWithError(
                        new IsLikedResponseWithError.Response(LUCAS),
                        null
                )
        );
        boolean isLiked = vkApi.isLiked(USER_ID, USER_ID, 0);
        assertTrue(isLiked);
    }

    @Test
    public void testNotLiked() {
        vkApiSimulator.successResponseForUrl(VkApiImpl.IS_LIKED_METHOD,
                new IsLikedResponseWithError(
                        new IsLikedResponseWithError.Response(NE_LUCAS),
                        null
                )
        );
        boolean isLiked = vkApi.isLiked(USER_ID, USER_ID, 0);
        assertFalse(isLiked);
    }

}
