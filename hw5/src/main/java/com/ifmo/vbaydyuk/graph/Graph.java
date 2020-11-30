package com.ifmo.vbaydyuk.graph;

import com.ifmo.vbaydyuk.drawingApi.DrawingApi;

import java.util.List;

public class Graph {
    /**
     * Bridge to drawing api
     */
    private final DrawingApi drawingApi;
    private final List<Vertex> vertices;
    private final List<Edge> edges;
    private static final int RADIUS = 5;

    protected Graph(DrawingApi drawingApi, List<Vertex> vertices, List<Edge> edges) {
        this.drawingApi = drawingApi;
        this.vertices = vertices;
        this.edges = edges;
    }

    private void drawVertex(Vertex vertex) {
        drawingApi.drawCircle(vertex.getX(), vertex.getY(), RADIUS);
    }

    private void drawEdge(Edge edge) {
        drawingApi.drawLine(edge.getFirst().getX(), edge.getFirst().getY(),
                edge.getSecond().getX(), edge.getSecond().getY());
    }

    public void drawGraph() {
        vertices.forEach(this::drawVertex);
        edges.forEach(this::drawEdge);
    }
}
