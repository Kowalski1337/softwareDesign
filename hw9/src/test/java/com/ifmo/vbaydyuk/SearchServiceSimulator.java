package com.ifmo.vbaydyuk;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableSet;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static javax.ws.rs.core.Response.ok;

public class SearchServiceSimulator extends Application {
    private final int port;
    private HttpServer httpServer;

    private final Map<SearchEngine, Response> responsesByEngine = new ConcurrentHashMap<>();
    private final Map<SearchEngine, Long> delaysByEngine = new ConcurrentHashMap<>();

    public SearchServiceSimulator(int port) {
        this.port = port;
    }

    public void start() {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(port), 0);
            HttpContext context = httpServer.createContext("/");
            HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(
                    this,
                    HttpHandler.class);
            context.setHandler(handler);
            httpServer.start();
        } catch (IOException e) {
            throw new IllegalStateException("Can't create web-service for SearchServiceSimulator", e);
        }
    }

    public void stop() {
        httpServer.stop(0);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return ImmutableSet.of(VkApiResource.class);
    }

    public void success(SearchResponse response) {
        this.responsesByEngine.put(response.getEngine(), ok(response).build());
    }

    public void clean() {
        delaysByEngine.clear();
    }

    public void withDelay(SearchEngine engine, long delay) {
        delaysByEngine.put(engine, delay);
    }

    @Path("/")
    public static class VkApiResource {
        @Context
        private Application application;

        @GET
        @Path("BING")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getAllFriends(
                @QueryParam("query") String query
        ) throws InterruptedException {
            Thread.sleep(getSimulator().delaysByEngine.getOrDefault(SearchEngine.BING, 0L));
            return getSimulator().responsesByEngine.get(SearchEngine.BING);
        }

        @GET
        @Path("GOOGLE")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getAllPosts(
                @QueryParam("query") String query
        ) throws InterruptedException {
            Thread.sleep(getSimulator().delaysByEngine.getOrDefault(SearchEngine.GOOGLE, 0L));
            return getSimulator().responsesByEngine.get(SearchEngine.GOOGLE);
        }

        @GET
        @Path("YANDEX")
        @Produces(MediaType.APPLICATION_JSON)
        public Response isLiked(
                @QueryParam("query") String query
        ) throws InterruptedException {
            Thread.sleep(getSimulator().delaysByEngine.getOrDefault(SearchEngine.YANDEX, 0L));
            return getSimulator().responsesByEngine.get(SearchEngine.YANDEX);
        }


        private SearchServiceSimulator getSimulator() {
            return (SearchServiceSimulator) application;
        }
    }
}
