import clock.MutableClock;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyMap;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventsStatisticTest {

    private static final Duration ONE_HOUR = Duration.ofHours(1);
    private static final Duration HALF_HOUR = Duration.ofMinutes(30);
    private static final String EVENT1 = "Event1";
    private static final String EVENT2 = "Event2";
    private static final String EVENT3 = "Event3";
    private static final String EVENT4 = "Event4";
    private static final String EVENT5 = "Event5";

    private final MutableClock clock = new MutableClock();
    private final EventsStatistic statistic = new EventsStatisticImpl(clock);

    @BeforeEach
    public void setUp() {
        statistic.reset();
    }

    @Test
    public void testStatisticByNameZeroEvents() {
        assertEquals(0, statistic.getEventStatisticByName(EVENT1));
    }

    @Test
    public void testAllStatisticZeroEvents() {
        assertThat(statistic.getAllAEventStatistic().entrySet(), empty());
    }

    @Test
    public void testStatisticByNameSingleEvent() {
        testStatisticByName(ImmutableMap.of(EVENT1, 2));
    }

    @Test
    public void testAllStatisticSingleEvent() {
        testAllStatistic(ImmutableMap.of(EVENT1, 2));
    }

    @Test
    public void testStatisticByNameMultipleEvents() {
        testStatisticByName(ImmutableMap.of(
                EVENT1, 2,
                EVENT2, 3,
                EVENT3, 15
        ));
    }

    @Test
    public void testAllStatisticMultipleEvents() {
        testAllStatistic(ImmutableMap.of(
                EVENT1, 2,
                EVENT2, 3,
                EVENT3, 15
        ));
    }

    @Test
    public void testStatisticByNameAfterOneHour() {
        fillEvents(ImmutableMap.of(
                EVENT1, 2,
                EVENT2, 3,
                EVENT3, 15
        ));
        clock.plus(ONE_HOUR);
        checkStatisticByName(emptyMap());
    }

    @Test
    public void testAllStatisticAfterOneHour() {
        fillEvents(ImmutableMap.of(
                EVENT1, 2,
                EVENT2, 3,
                EVENT3, 15
        ));
        clock.plus(ONE_HOUR);
        checkAllStatistic(emptyMap());
    }

    @Test
    public void testStatisticByNameAfterHalfHour() {
        ImmutableMap<String, Integer> eventCount = ImmutableMap.of(
                EVENT1, 2,
                EVENT2, 3,
                EVENT3, 15
        );
        fillEvents(eventCount);
        clock.plus(HALF_HOUR);
        checkStatisticByName(eventCount);
    }


    @Test
    public void testAllStatisticAfterHalfHour() {
        ImmutableMap<String, Integer> eventCount = ImmutableMap.of(
                EVENT1, 2,
                EVENT2, 3,
                EVENT3, 15
        );
        fillEvents(eventCount);
        clock.plus(HALF_HOUR);
        checkAllStatistic(eventCount);
    }

    @Test
    public void testNewStatisticByNameAfterOneHour() {
        fillEvents(ImmutableMap.of(
                EVENT1, 2,
                EVENT2, 3,
                EVENT3, 15
        ));
        clock.plus(ONE_HOUR);
        testStatisticByName(ImmutableMap.of(
                EVENT1, 2,
                EVENT2, 3,
                EVENT4, 5,
                EVENT5, 7
        ));
    }

    @Test
    public void testNewAllStatisticAfterOneHour() {
        fillEvents(ImmutableMap.of(
                EVENT1, 2,
                EVENT2, 3,
                EVENT3, 15
        ));
        clock.plus(ONE_HOUR);
        testAllStatistic(ImmutableMap.of(
                EVENT1, 2,
                EVENT2, 3,
                EVENT4, 5,
                EVENT5, 7
        ));
    }

    private void testStatisticByName(Map<String, Integer> eventCount) {
        fillEvents(eventCount);
        checkStatisticByName(eventCount);
    }

    private void checkStatisticByName(Map<String, Integer> eventCount) {
        eventCount.forEach((event, count) -> assertEquals((double) count / ONE_HOUR.toMinutes(),
                statistic.getEventStatisticByName(event)));
    }

    private void testAllStatistic(Map<String, Integer> eventCount) {
        fillEvents(eventCount);
        checkAllStatistic(eventCount);
    }

    private void checkAllStatistic(Map<String, Integer> eventCount) {
        Map<String, Double> eventStatistic = eventCount
                .entrySet()
                .stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> (double) e.getValue() / ONE_HOUR.toMinutes()
                ));
        assertEquals(eventStatistic, statistic.getAllAEventStatistic());
    }

    private void fillEvents(Map<String, Integer> eventCount) {
        eventCount.forEach((event, count) -> {
            for (int i = 0; i < count; ++i)
                statistic.incEvent(event);
        });
    }

}