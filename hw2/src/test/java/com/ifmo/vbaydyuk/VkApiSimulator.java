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

public class VkApiSimulator extends Application {
    private final int port;
    private HttpServer httpServer;

    private final Map<Object, Response> responsesByUrl = new ConcurrentHashMap<>();

    public VkApiSimulator(int port) {
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
            throw new IllegalStateException("Can't create web-service for VkApiSimulator", e);
        }
    }

    public void stop() {
        httpServer.stop(0);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return ImmutableSet.of(VkApiResource.class);
    }

    public void successResponseForUrl(String url, Object response) {
        this.responsesByUrl.put(url, ok(response).build());
    }

    @Path("/")
    public static class VkApiResource {
        @Context
        private Application application;

        @GET
        @Path(VkApiImpl.GET_FRIENDS_METHOD)
        @Produces(MediaType.APPLICATION_JSON)
        public Response getAllFriends(
                @QueryParam(VkApiImpl.USER_ID) int userId,
                @QueryParam(VkApiImpl.USER_FIELDS) String userFields,
                @QueryParam(VkApiImpl.ACCESS_TOKEN) String accessToken,
                @QueryParam(VkApiImpl.VERSION) String version
        ) {
            return getSimulator().responsesByUrl.get(VkApiImpl.GET_FRIENDS_METHOD);
        }

        @GET
        @Path(VkApiImpl.WALL_GET_METHOD)
        @Produces(MediaType.APPLICATION_JSON)
        public Response getAllPosts(
                @QueryParam(VkApiImpl.OWNER_ID) int ownerId,
                @QueryParam(VkApiImpl.COUNT) int count,
                @QueryParam(VkApiImpl.ACCESS_TOKEN) String accessToken,
                @QueryParam(VkApiImpl.VERSION) String version
        ) {
            return getSimulator().responsesByUrl.get(VkApiImpl.WALL_GET_METHOD);
        }

        @GET
        @Path(VkApiImpl.IS_LIKED_METHOD)
        @Produces(MediaType.APPLICATION_JSON)
        public Response isLiked(
                @QueryParam(VkApiImpl.OWNER_ID) int ownerId,
                @QueryParam(VkApiImpl.COUNT) int count,
                @QueryParam(VkApiImpl.ACCESS_TOKEN) String accessToken,
                @QueryParam(VkApiImpl.VERSION) String version
        ) {
            return getSimulator().responsesByUrl.get(VkApiImpl.IS_LIKED_METHOD);
        }


        private VkApiSimulator getSimulator() {
            return (VkApiSimulator) application;
        }
    }
}
