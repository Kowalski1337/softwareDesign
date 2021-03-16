package com.ifmo.vbaydyuk;

import akka.actor.UntypedActor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class ChildActor extends UntypedActor {

    private final SearchEngine searchEngine;

    public ChildActor(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    @Override
    public void onReceive(Object o) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:8080/%s", searchEngine))
                .queryParam("query", o.toString());

        SearchResponse response = restTemplate.exchange(uriComponentsBuilder.toUriString(),
                HttpMethod.GET, HttpEntity.EMPTY, SearchResponse.class).getBody();

        getContext().parent().tell(response, self());
    }
}
