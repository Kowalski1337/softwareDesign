import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventsStatisticImpl implements EventsStatistic {

    private static final Duration ONE_HOUR = Duration.ofHours(1);
    public static final double ZERO = 0.0;
    private final Map<String, List<Instant>> events;
    private final Clock clock;

    public EventsStatisticImpl(Clock clock) {
        this.clock = clock;
        this.events = new HashMap<>();
    }

    @Override
    public void incEvent(String name) {
        events.computeIfAbsent(name, n -> new ArrayList<>());
        events.get(name).add(clock.instant());
    }

    @Override
    public double getEventStatisticByName(String name) {
        return getAllAEventStatistic().getOrDefault(name, ZERO);
    }

    @Override
    public Map<String, Double> getAllAEventStatistic() {
        Instant hourAgo = clock.instant().minus(ONE_HOUR);
        return events.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> (double) e.getValue().stream()
                                .filter(event -> event.isAfter(hourAgo))
                                .count() / ONE_HOUR.toMinutes()
                ))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() != 0.0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void reset() {
        events.forEach((name, instants) -> events.remove(name));
    }
}
