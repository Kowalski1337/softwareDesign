package com.ifmo.vbaydyuk.hw2;

import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.ifmo.vbaydyuk.hw2.VKPostParser.MILLIS_PER_SECOND;
import static com.ifmo.vbaydyuk.hw2.VKPostParser.SECONDS_PER_HOUR;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class VKPostParserTest {
    private VKPostJSONGenerator jsonGenerator = new VKPostJSONGenerator();
    private VKPostParser parser = new VKPostParser();

    @Test
    public void testHoursOldFormat() {
        Date date1 = new Date(System.currentTimeMillis() - MILLIS_PER_SECOND * SECONDS_PER_HOUR * 3 / 2);
        Date date2 = new Date(System.currentTimeMillis() - MILLIS_PER_SECOND * SECONDS_PER_HOUR * 5 / 2);
        Date date3 = new Date(System.currentTimeMillis() - MILLIS_PER_SECOND * SECONDS_PER_HOUR * 11 / 8);
        assertEquals(1, VKPostParser.hoursOldFormat(date1));
        assertEquals(1, VKPostParser.hoursOldFormat(date3));
        assertEquals(2, VKPostParser.hoursOldFormat(date2));
    }

    @Test
    public void testGetPosts() {
        long[][] dates = new long[30][30];
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                dates[i][j] = Math.abs(random.nextInt());
            }
        }
        Reader response = new StringReader(jsonGenerator.generateHttpResponse(dates[0]));
        List<VKPost> posts = parser.getPosts(response);
        long[] actualDates = posts.stream()
                .map(VKPost::getDate)
                .mapToLong(Date::getTime)
                .map(n -> n / MILLIS_PER_SECOND)
                .toArray();
        assertArrayEquals(dates[0], actualDates);
    }
}