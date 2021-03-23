package com.ifmo.vbaydyuk;

import akka.actor.ActorRef;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class SearchTest {
    SearchServiceSimulator simulator;

    private static final SearchResponse GOOGLE_RESPONSE = new SearchResponse(Arrays.asList(
            new SearchResponse.SingleResponse("google1"),
            new SearchResponse.SingleResponse("google2"),
            new SearchResponse.SingleResponse("google3"),
            new SearchResponse.SingleResponse("google4"),
            new SearchResponse.SingleResponse("google5")
    ), SearchEngine.GOOGLE);

    private static final SearchResponse BING_RESPONSE = new SearchResponse(Arrays.asList(
            new SearchResponse.SingleResponse("bing1"),
            new SearchResponse.SingleResponse("bing2"),
            new SearchResponse.SingleResponse("bing3"),
            new SearchResponse.SingleResponse("bing4"),
            new SearchResponse.SingleResponse("bing5")
    ), SearchEngine.BING);

    private static final SearchResponse YANDEX_RESPONSE = new SearchResponse(Arrays.asList(
            new SearchResponse.SingleResponse("yandex1"),
            new SearchResponse.SingleResponse("yandex2"),
            new SearchResponse.SingleResponse("yandex3"),
            new SearchResponse.SingleResponse("yandex4"),
            new SearchResponse.SingleResponse("yandex5")
    ), SearchEngine.YANDEX);

    @Before
    public void setUp() {
        simulator = new SearchServiceSimulator(8080);
        simulator.start();
        simulator.success(GOOGLE_RESPONSE);
        simulator.success(BING_RESPONSE);
        simulator.success(YANDEX_RESPONSE);
    }

    @After
    public void tearDown() {
        simulator.stop();
    }

    @Test
    public void testNoDelay() {
        test(15);
    }

    @Test
    public void testDelayForAll() {
        simulator.withDelay(SearchEngine.GOOGLE, 1000);
        simulator.withDelay(SearchEngine.YANDEX, 1000);
        simulator.withDelay(SearchEngine.BING, 1000);
        test(0);
    }

    @Test
    public void testDifferentDelays() {
        simulator.withDelay(SearchEngine.YANDEX, 50);
        simulator.withDelay(SearchEngine.BING, 1000);
        test(10);
    }

    private void test(int expected) {
        QueryManager queryManager = new QueryManager();
        ActorRef ref = queryManager.processQuery("a");
        while (!ref.isTerminated()) {
        }
        assertEquals(expected,
                queryManager.getQueryResponseMap().get("a").values().stream()
                        .map(SearchResponse::getResults)
                        .mapToLong(List::size)
                        .sum());
        queryManager.shutdown();
        simulator.clean();
    }

}