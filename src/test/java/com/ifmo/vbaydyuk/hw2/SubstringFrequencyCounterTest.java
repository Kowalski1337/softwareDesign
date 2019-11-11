package com.ifmo.vbaydyuk.hw2;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.when;

public class SubstringFrequencyCounterTest {
    private static VKPostJSONGenerator jsonGenerator = new VKPostJSONGenerator();
    private static SubstringFrequencyCounter frequencyCounter;
    private static VKPostFetcher fetcher;

    private int[][] dates = {
            {5, 6, 2, 2, 2},
            {1, 1, 1, 1, 1},
            {7, 7, 7, 7, 3, 3, 3, 3, 3, 1, 1, 1, 9},
            {1, 2, 3, 4, 5, 6}
    };
    private int[][] expectedResult = {
            {0, 3, 0, 0, 1, 1, 0, 0},
            {5},
            {3, 0, 5, 0, 0, 0, 4, 0, 1},
            {1, 1, 1, 1, 1, 1, 0, 0}
    };

    @Test
    public void testRun() {
        fetcher = Mockito.mock(VKPostFetcher.class);
        frequencyCounter = new SubstringFrequencyCounter(fetcher, new VKPostParser());
        for (int i = 0; i < dates.length; i++) {
            testRun(expectedResult[i], dates[i]);
        }
    }

    private static void testRun(int[] expectedResult, int[] dates) {
        String searchText = "";

        Reader response = new StringReader(jsonGenerator.generateHttpResponseByHours(dates));

        when(fetcher.fetch(searchText)).thenReturn(response);

        int[] actualResult = frequencyCounter.run(searchText, expectedResult.length);
        assertArrayEquals(expectedResult, actualResult);
    }
}
