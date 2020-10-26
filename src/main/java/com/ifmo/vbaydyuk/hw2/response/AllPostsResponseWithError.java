package com.ifmo.vbaydyuk.hw2.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifmo.vbaydyuk.hw2.response.error.Error;

import java.util.List;

/**
 * Response of get all posts request
 *
 * @author vbaydyuk
 * @since 19.10.2020
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllPostsResponseWithError {

    @JsonProperty("response")
    public Response response;

    @JsonProperty("error")
    public
    Error error;

    public AllPostsResponseWithError(
            @JsonProperty("response") Response response,
            @JsonProperty("error") Error error) {
        this.response = response;
        this.error = error;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        @JsonProperty(value = "count", required = true)
        public
        int count;
        @JsonProperty(value = "items", required = true)
        public
        List<Post> items;

        public Response(
                @JsonProperty("count") int count,
                @JsonProperty(value = "items", required = true) List<Post> items) {
            this.count = count;
            this.items = items;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Post {
        @JsonProperty(value = "id", required = true)
        public
        int id;

        public Post(@JsonProperty(value = "id", required = true) int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
