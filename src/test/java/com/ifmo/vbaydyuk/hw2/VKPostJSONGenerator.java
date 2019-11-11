package com.ifmo.vbaydyuk.hw2;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.ifmo.vbaydyuk.hw2.VKPostParser.DATE;
import static com.ifmo.vbaydyuk.hw2.VKPostParser.ITEMS;
import static com.ifmo.vbaydyuk.hw2.VKPostParser.MILLIS_PER_SECOND;
import static com.ifmo.vbaydyuk.hw2.VKPostParser.RESPONSE;
import static com.ifmo.vbaydyuk.hw2.VKPostParser.SECONDS_PER_HOUR;
import static com.ifmo.vbaydyuk.hw2.VKPostParser.TEXT;

public class VKPostJSONGenerator {
    private static final String OPEN_PARENTHESIS = "{";
    private static final String CLOSE_PARENTHESIS = "}";
    private static final String QUOTE = "\"";
    private static final String COLON = ":";
    private static final String DELIMITER = ", ";


    public String generateHttpResponseByHours(int[] dates) {
        long[] unixdates = Arrays.stream(dates)
                .mapToLong(VKPostJSONGenerator::getUnixTime)
                .toArray();
        return generateHttpResponse(unixdates);
    }

    public String generateHttpResponse(long[] dates) {
        StringBuilder result = new StringBuilder();

        String beginning = OPEN_PARENTHESIS + QUOTE + RESPONSE + QUOTE + COLON + OPEN_PARENTHESIS + QUOTE + ITEMS + QUOTE + COLON + "[";
        String end = "]" + CLOSE_PARENTHESIS + CLOSE_PARENTHESIS;

        result.append(beginning)
                .append(Arrays.stream(dates).boxed().map(VKPostJSONGenerator::generatePostJSON).collect(Collectors.joining(DELIMITER)))
                .append(end);

        return result.toString();
    }

    private static String generatePostJSON(long date) {
        String s = "{\"date\"";
        return OPEN_PARENTHESIS + QUOTE + DATE + QUOTE + COLON + date + DELIMITER + QUOTE + TEXT
                + QUOTE + COLON + QUOTE + QUOTE + "" + CLOSE_PARENTHESIS;
    }

    private static long getUnixTime(int hoursOld) {
        return (System.currentTimeMillis() - hoursOld * MILLIS_PER_SECOND * SECONDS_PER_HOUR + 100_000) / MILLIS_PER_SECOND;
    }
}
