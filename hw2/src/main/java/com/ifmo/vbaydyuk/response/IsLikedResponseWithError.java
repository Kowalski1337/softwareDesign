package com.ifmo.vbaydyuk.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ifmo.vbaydyuk.response.error.Error;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IsLikedResponseWithError {
    @JsonProperty("response")
    public
    Response response;

    @JsonProperty("error")
    public
    Error error;

    public IsLikedResponseWithError(
            @JsonProperty("response") Response response,
            @JsonProperty("error") Error error) {
        this.response = response;
        this.error = error;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Response {
        @JsonProperty(value = "liked", required = true)
        public
        int liked;

        public Response(@JsonProperty(value = "liked", required = true) int liked) {
            this.liked = liked;
        }
    }
}