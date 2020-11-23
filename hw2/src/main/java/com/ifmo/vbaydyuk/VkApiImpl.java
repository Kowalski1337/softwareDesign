package com.ifmo.vbaydyuk;

import com.ifmo.vbaydyuk.response.IsLikedResponseWithError;
import com.ifmo.vbaydyuk.response.AllFriendsResponseWithError;
import com.ifmo.vbaydyuk.response.AllPostsResponseWithError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

import static com.ifmo.vbaydyuk.response.error.VkApiErrors.TO_MANY_REQUESTS;

/**
 * Simple implementation of {@link VkApi}
 *
 * @author vbaydyuk
 * @since 18.10.2020
 */
public class VkApiImpl implements VkApi {
    private static final Logger log = LoggerFactory.getLogger(VkApiImpl.class);
    private static final String TOKEN = "aa865a12c181f119e76211ea25714c0e3f08f3f1bf97ea72e82c99ad7c2b53e88f6c9e525d94a0c981b39";
    public static final String USER_ID = "user_id";
    public static final String USER_FIELDS = "fields";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String VERSION = "v";
    public static final String VK_VERSION = "5.124";
    public static final String OWNER_ID = "owner_id";
    public static final String ITEM_ID = "item_id";
    public static final String ITEM_TYPE = "type";
    public static final String POST = "post";
    public static final String COUNT = "count";
    public static final String GET_FRIENDS_METHOD = "friends.get";
    public static final String IS_LIKED_METHOD = "likes.isLiked";
    public static final String WALL_GET_METHOD = "wall.get";

    public static final String VK_API_URL = "https://api.vk.com/method/";

    private final String vkApiUrl;

    public VkApiImpl(String vkApiUrl) {
        this.vkApiUrl = vkApiUrl;
    }


    @Nullable
    @Override
    public List<VkUser> getAllFriends(int userId) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(vkApiUrl + GET_FRIENDS_METHOD)
                .queryParam(USER_ID, userId)
                .queryParam(USER_FIELDS, "first_name,last_name")
                .queryParam(ACCESS_TOKEN, TOKEN)
                .queryParam(VERSION, VK_VERSION);
        AllFriendsResponseWithError response = null;
        while (response == null || (response.error != null && response.error.error_code == TO_MANY_REQUESTS)) {
            response = restTemplate.exchange(uriComponentsBuilder.toUriString(),
                    HttpMethod.GET, HttpEntity.EMPTY, AllFriendsResponseWithError.class).getBody();
        }
        if (response.error != null) {
            log.error("Cannot get friends for user {}, cause: {}", userId, response.error.error_msg);
            return null;
        }
        log.info("{} friends found for user {}", response.response.count, userId);
        return response.response.items
                .stream()
                .filter(AllFriendsResponseWithError.User::isActive)
                .map(user -> new VkUser(user.id, user.first_name, user.last_name))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isLiked(int userId, int ownerId, int postId) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(vkApiUrl + IS_LIKED_METHOD)
                .queryParam(USER_ID, userId)
                .queryParam(OWNER_ID, ownerId)
                .queryParam(ITEM_ID, postId)
                .queryParam(ITEM_TYPE, POST)
                .queryParam(ACCESS_TOKEN, TOKEN)
                .queryParam(VERSION, VK_VERSION);
        IsLikedResponseWithError response = null;
        while (response == null || (response.error != null && response.error.error_code == TO_MANY_REQUESTS)) {
            response = restTemplate.exchange(uriComponentsBuilder.toUriString(),
                    HttpMethod.GET, HttpEntity.EMPTY, IsLikedResponseWithError.class).getBody();
        }
        if (response.error != null) {
            log.error("Cannot check if user {} liked {} post of {} user, cause: {}", userId, postId, ownerId, response.error.error_msg);
            return false;
        }
        boolean ans = response.response.liked == 1;
        log.info("User {} {} {} post of {} user", userId, (ans ? "liked" : "didn't like"), postId, ownerId);
        return ans;
    }

    @Nullable
    @Override
    public List<Integer> getAllPosts(int userId, int count) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(vkApiUrl + WALL_GET_METHOD)
                .queryParam(OWNER_ID, userId)
                .queryParam(COUNT, count)
                .queryParam(ACCESS_TOKEN, TOKEN)
                .queryParam(VERSION, VK_VERSION);
        AllPostsResponseWithError response = null;
        while (response == null || (response.error != null && response.error.error_code == TO_MANY_REQUESTS)) {
            response = restTemplate.exchange(uriComponentsBuilder.toUriString(),
                    HttpMethod.GET, HttpEntity.EMPTY, AllPostsResponseWithError.class).getBody();
        }
        if (response.error != null) {
            log.error("Cannot get posts for user {}, cause: {}", userId, response.error.error_msg);
            return null;
        }
        log.info("{} posts found for user {}", response.response.count, userId);
        return response.response.items
                .stream()
                .map(AllPostsResponseWithError.Post::getId)
                .collect(Collectors.toList());
    }
}
