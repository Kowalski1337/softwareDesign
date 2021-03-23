package com.ifmo.vbaydyuk.service;

import com.ifmo.vbaydyuk.event.LeaveEvent;
import com.ifmo.vbaydyuk.event.VisitEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticsServiceTest extends ServiceTestBase {
    private StatisticsService statisticsService;
    private static final LocalDateTime FIRST_DAY = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
    private static final LocalDateTime SECOND_DAY = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIN);
    private static final LocalDateTime THIRD_DAY = LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.MIN);

    @BeforeEach
    @Override
    void setUp() {
        super.setUp();
        statisticsService = new StatisticsService(eventStorage, ticketStorage);
    }

    @Test
    public void testVisitPerDate() {
        init();

        assertThat(statisticsService.visitPerDate(), allOf(
                hasEntry(FIRST_DAY.toLocalDate(), 3),
                hasEntry(SECOND_DAY.toLocalDate(), 1),
                hasEntry(THIRD_DAY.toLocalDate(), 1)
        ));
    }

    @Test
    public void testAvgFreq() {
        init();
        assertEquals(5.0 / 3, statisticsService.averageFreq());
    }

    @Test
    public void testAvgDuration() {
        init();
        assertEquals(((double)((120 + 90 + 45 + 30 + 120) * 60 * 1000)) / 5, statisticsService.averageVisitTime());
    }

    private void init() {
        managerService.createTicket(TICKET_ID1, EXPIRATION_DATE2);
        visit(FIRST_DAY);
        leave(FIRST_DAY.plusHours(2));
        visit(FIRST_DAY.plusHours(4));
        leave(FIRST_DAY.plusHours(5).plusMinutes(30));
        visit(FIRST_DAY.plusHours(10));
        leave(FIRST_DAY.plusHours(10).plusMinutes(45));
        visit(SECOND_DAY);
        leave(SECOND_DAY.plusMinutes(30));
        visit(THIRD_DAY);
        leave(THIRD_DAY.plusHours(2));
    }

    private void visit(LocalDateTime visitTime) {
        new VisitEvent(TICKET_ID1, visitTime).handle(eventStorage, ticketStorage);
    }

    private void leave(LocalDateTime leaveTime) {
        new LeaveEvent(TICKET_ID1, leaveTime).handle(eventStorage, ticketStorage);
    }
}
