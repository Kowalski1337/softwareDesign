package com.ifmo.vbaydyuk.graph;

import com.ifmo.vbaydyuk.drawingApi.DrawingApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GraphFactory {
    private static final String TYPE = System.getProperty("graph.type");
    private static final String LIST_GRAPH_FILE = System.getProperty("list.graph");
    private static final String MATRIX_GRAPH_FILE = System.getProperty("matrix.graph");
    private static final double INDENT = 0.1;

    public Graph getGraph(DrawingApi drawingApi) {
        try (Scanner scanner = new Scanner(getClass().getClassLoader().
                getResource(TYPE.equals("list") ? LIST_GRAPH_FILE : MATRIX_GRAPH_FILE).openStream())) {
            List<Vertex> vertices = new ArrayList<>();
            List<Edge> edges = new ArrayList<>();
            double centerX = drawingApi.getDrawingAreaWidth() / 2.0;
            double centerY = drawingApi.getDrawingAreaWidth() / 2.0;
            double minSide = Math.min(drawingApi.getDrawingAreaHeight(), drawingApi.getDrawingAreaWidth());
            double radius = minSide / 2.0 - INDENT * minSide;
            int nVertices = scanner.nextInt();
            double corner = 2 * Math.PI / nVertices;
            for (int i = 0; i < nVertices; i++) {
                vertices.add(new Vertex(
                        centerX + radius * Math.cos(i * corner),
                        centerY + radius * Math.sin(i * corner)
                ));
            }
            switch (TYPE) {
                case "matrix":
                    for (int i = 0; i < nVertices; i++) {
                        for (int j = 0; j < nVertices; j++) {
                            int isConnected = scanner.nextInt();
                            if (isConnected == 1) {
                                edges.add(new Edge(vertices.get(i), vertices.get(j)));
                            }
                        }
                    }
                    break;
                case "list":
                    int nEdges = scanner.nextInt();
                    for (int i = 0; i < nEdges; i++) {
                        int first = scanner.nextInt();
                        int second = scanner.nextInt();
                        edges.add(new Edge(vertices.get(first - 1), vertices.get(second - 1)));
                    }
                    break;
            }
            return new Graph(drawingApi, vertices, edges);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
