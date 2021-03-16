package com.ifmo.vbaydyuk;

import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.Success;
import org.bson.Document;
import rx.Observable;


public class MongoService {

    private static final MongoClient CLIENT = MongoClients.create("mongodb://localhost:27017");
    private static final String DB_NAME = "rxtest";
    private static final String USERS_COLLECTION_NAME = "users";
    private static final String GOODS_COLLECTION_NAME = "goods";

    public static Observable<Success> addUser(String id, String currency) {
        return CLIENT.getDatabase(DB_NAME).getCollection(USERS_COLLECTION_NAME).insertOne(new Document(id, currency));
    }

    public static Observable<Document> getGoods() {
        return CLIENT.getDatabase(DB_NAME).getCollection(GOODS_COLLECTION_NAME).find().toObservable();
    }

    public static Observable<String> getUserCurrency(String id) {
        return CLIENT.getDatabase(DB_NAME).getCollection(USERS_COLLECTION_NAME).find().toObservable()
                .filter(document -> document.containsKey(id))
                .take(1)
                .map(document -> String.valueOf(document.get(id)));
    }

    public static Observable<Success> addGood(String name, double price) {
        return CLIENT.getDatabase(DB_NAME).getCollection(GOODS_COLLECTION_NAME).insertOne(new Document(name, price));
    }
}
