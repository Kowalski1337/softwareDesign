import java.util.Map;


public interface EventsStatistic {
    void incEvent(String name);

    double getEventStatisticByName(String name);

    Map<String, Double> getAllAEventStatistic();

    default void printStatistic() {
        System.out.println(getAllAEventStatistic());
    }

    void reset();
}
