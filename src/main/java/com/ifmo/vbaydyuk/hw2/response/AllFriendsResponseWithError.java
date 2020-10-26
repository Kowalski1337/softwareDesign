package com.ifmo.vbaydyuk.hw2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifmo.vbaydyuk.hw2.response.error.Error;

import java.util.List;

/**
 * Response of get all friends request
 *
 * @author vbaydyuk
 * @since 19.10.2020
 */
@SuppressWarnings("unused")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllFriendsResponseWithError {

    @JsonProperty("response")
    public
    Response response;

    @JsonProperty("error")
    public
    Error error;

    public AllFriendsResponseWithError(
            @JsonProperty("response") Response response,
            @JsonProperty("error") Error error) {
        this.response = response;
        this.error = error;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        @JsonProperty("count")
        public
        int count;
        @JsonProperty(value = "items", required = true)
        public
        List<User> items;

        public Response(
                @JsonProperty("count") int count,
                @JsonProperty(value = "items", required = true) List<User> items
        ) {
            this.count = count;
            this.items = items;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        @JsonProperty(value = "id", required = true)
        public
        int id;
        @JsonProperty("deactivated")
        String deactivated;
        @JsonProperty(value = "first_name", required = true)
        public
        String first_name;
        @JsonProperty(value = "last_name", required = true)
        public
        String last_name;

        public User(
                @JsonProperty(value = "id", required = true) int id,
                @JsonProperty("deactivated") String deactivated,
                @JsonProperty(value = "first_name", required = true) String first_name,
                @JsonProperty(value = "last_name", required = true) String last_name) {
            this.id = id;
            this.deactivated = deactivated;
            this.first_name = first_name;
            this.last_name = last_name;
        }

        public boolean isActive() {
            return deactivated == null || deactivated.equals("null");
        }
    }
}
