package com.ifmo.vbaydyuk;


import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;
import scala.concurrent.duration.Duration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MasterActor extends UntypedActor {
    public static final Duration TIMEOUT = Duration.fromNanos(500_000_000);
    private static int number = 0;

    private final Map<String, Map<String, SearchResponse>> queryResponseMap;
    private final Map<String, SearchResponse> responseMap = new HashMap<>();

    public MasterActor(Map<String, Map<String, SearchResponse>> queryResponseMap) {
        getContext().setReceiveTimeout(TIMEOUT);
        this.queryResponseMap = queryResponseMap;
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof SearchResponse) {
            handleSearchResponse((SearchResponse) message);
        }

        if (message instanceof String) {
            handleQuery((String) message);
        }

        if (message instanceof ReceiveTimeout) {
            stop();
        }
    }

    private void handleQuery(String message) {
        this.queryResponseMap.put(message, responseMap);
        Arrays.stream(SearchEngine.values())
                .map(this::createChildActor)
                .forEach(actorRef -> actorRef.tell(message, self()));
    }

    private void handleSearchResponse(SearchResponse searchResponse) {
        this.responseMap.put(searchResponse.getEngine().toString(), searchResponse);
        if (responseMap.keySet().size() == 3) {
            stop();
        }
    }

    private void stop() {
        printResult();
        getContext().stop(self());
    }

    private void printResult() {
        responseMap.forEach(
                (engine, response) -> {
                    System.out.printf("Result from Engine: %s%n", engine);
                    response.getResults()
                            .stream()
                            .map(SearchResponse.SingleResponse::getLink)
                            .forEach(System.out::println);
                }
        );
    }

    private ActorRef createChildActor(SearchEngine engine) {
        String name = String.format("child_%s_%d", engine, number++);
        return getContext().actorOf(Props.create(ChildActor.class, engine), name);
    }
}
