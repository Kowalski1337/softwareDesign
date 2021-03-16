package com.ifmo.vbaydyuk;

import com.mongodb.rx.client.Success;
import org.bson.Document;
import rx.Observable;

import java.util.stream.Collectors;

public class CatalogueManager {

    public static Observable<Success> addUser(String id, String currency) {
        return MongoService.addUser(id, currency);
    }


    public static Observable<String> observeGoods(String id) {
        return MongoService.getUserCurrency(id).flatMap(
                currency -> MongoService.getGoods()
                        .map(good -> goodToString(currency, good))
        ).defaultIfEmpty("No goods in the store");
    }

    private static String goodToString(String currencyString, Document good) {
        Currency currency = Currency.fromString(currencyString);
        return good.entrySet().stream()
                .filter(entry -> !entry.getKey().equals("_id"))
                .map(entry ->
                        String.format("%s : %f %s\n", entry.getKey(),
                                Currency.convertTo(currency,
                                        Double.parseDouble(entry.getValue().toString())
                                ),
                                currency.toString()))
                .collect(Collectors.joining());
    }

    public static Observable<Success> addGood(String name, double price) {
        return MongoService.addGood(name, price);
    }
}
