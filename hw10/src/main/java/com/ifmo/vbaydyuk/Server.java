package com.ifmo.vbaydyuk;

import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

import java.util.Arrays;
import java.util.List;

public class Server {

    public static void main(String[] args) {
        HttpServer.newServer(8080)
                .start((request, response) -> {
                    Observable<String> responseObservable = from(request).handle();
                    return response.writeString(responseObservable);
                }).awaitShutdown();
    }

    private static Request from(HttpServerRequest<ByteBuf> request) {
        String path = request.getDecodedPath().substring(1);
        List<String> params = Arrays.asList(path.split("/"));

        if (params.get(0).equals("register")) {
            return new RegisterRequest(params.get(1), params.get(2));
        }
        if (params.get(0).equals("list")) {
            return new GetGoodsRequest(params.get(1));
        }
        if (params.get(0).equals("add")) {
            return new AddGoodRequest(params.get(1), Double.parseDouble(params.get(2)));
        }
        throw new IllegalArgumentException();
    }


    private interface Request {
        Observable<String> handle();
    }

    private static class RegisterRequest implements Request {
        private final String id;
        private final String currency;

        public RegisterRequest(String id, String currency) {
            this.id = id;
            this.currency = currency;
        }

        @Override
        public Observable<String> handle() {
            return CatalogueManager.addUser(id, currency).map(s -> "User has been successfully added!");
        }
    }

    private static class GetGoodsRequest implements Request {
        private final String id;

        public GetGoodsRequest(String id) {
            this.id = id;
        }

        @Override
        public Observable<String> handle() {
            return CatalogueManager.observeGoods(id);
        }
    }

    private static class AddGoodRequest implements Request {
        private final String name;
        private final double price;

        public AddGoodRequest(String name, double price) {
            this.name = name;
            this.price = price;
        }

        @Override
        public Observable<String> handle() {
            return CatalogueManager.addGood(name, price).map(s -> "Good successfully added!");
        }
    }
}
