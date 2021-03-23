package com.ifmo.vbaydyuk;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {
    @JsonProperty("results")
    private final List<SingleResponse> results;
    @JsonProperty("engine")
    private final SearchEngine engine;

    public SearchResponse(@JsonProperty("results") List<SingleResponse> results,
                          @JsonProperty("engine") SearchEngine engine) {
        this.results = results;
        this.engine = engine;
    }

    public List<SingleResponse> getResults() {
        return results;
    }

    public SearchEngine getEngine() {
        return engine;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SingleResponse {
        @JsonProperty("link")
        private final String link;

        public SingleResponse(@JsonProperty("link") String link) {
            this.link = link;
        }

        public String getLink() {
            return link;
        }

        public static SingleResponse from(Object o) {
            return new SingleResponse(o.toString());
        }
    }

}
