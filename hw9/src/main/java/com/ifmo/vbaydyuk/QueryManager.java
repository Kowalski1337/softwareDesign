package com.ifmo.vbaydyuk;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.HashMap;
import java.util.Map;

public class QueryManager {
    private final ActorSystem system = ActorSystem.create("actor-system");
    private int masterActorNumber = 0;

    private final Map<String, Map<String, SearchResponse>> queryResponseMap = new HashMap<>();

    public Map<String, Map<String, SearchResponse>> getQueryResponseMap() {
        return queryResponseMap;
    }

    public ActorRef processQuery(String query) {
        ActorRef masterActor = system.actorOf(Props.create(MasterActor.class, queryResponseMap),
                String.format("master-actor-%d", masterActorNumber++));
        masterActor.tell(query, ActorRef.noSender());
        return masterActor;
    }

    public void shutdown() {
        system.terminate();
    }
}
