package com.ifmo.vbaydyuk.graph;

public class Edge {
    private final Vertex first;
    private final Vertex second;

    public Edge(Vertex first, Vertex second) {
        this.first = first;
        this.second = second;
    }

    public Vertex getFirst() {
        return first;
    }

    public Vertex getSecond() {
        return second;
    }
}
