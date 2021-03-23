package com.ifmo.vbaydyuk.service;

import com.ifmo.vbaydyuk.event.CreateTicketEvent;
import com.ifmo.vbaydyuk.event.Event;
import com.ifmo.vbaydyuk.event.LeaveEvent;
import com.ifmo.vbaydyuk.event.VisitEvent;
import com.ifmo.vbaydyuk.service.clock.MutableClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EntranceServiceTest extends ServiceTestBase {
    private EntranceService entranceService;
    private MutableClock clock;

    @BeforeEach
    @Override
    void setUp() {
        super.setUp();
        clock = new MutableClock();
        entranceService = new EntranceService(eventStorage, ticketStorage, clock);
    }

    @Test
    public void testNotExistedTicket() {
        assertThrows(IllegalArgumentException.class, () -> entranceService.visit(TICKET_ID1));
        assertThrows(IllegalArgumentException.class, () -> managerService.getInfo(TICKET_ID1));
        assertEquals(eventStorage.getEventsByTicket(), emptyMap());

        assertThrows(IllegalArgumentException.class, () -> entranceService.leave(TICKET_ID1));
        assertThrows(IllegalArgumentException.class, () -> managerService.getInfo(TICKET_ID1));
        assertEquals(eventStorage.getEventsByTicket(), emptyMap());
    }

    @Test
    public void testVisit() {
        managerService.createTicket(TICKET_ID1, EXPIRATION_DATE1);
        entranceService.visit(TICKET_ID1);

        assertTrue(managerService.getInfo(TICKET_ID1).isHere());
        List<Event> events = eventStorage.getEvents(TICKET_ID1);
        assertThat(events, hasSize(2));
        assertThat(events, hasItems(
                equalTo(new CreateTicketEvent(TICKET_ID1, EXPIRATION_DATE1)),
                allOf(
                        instanceOf(VisitEvent.class),
                        hasProperty("ticketId", equalTo(TICKET_ID1))
                )
        ));
    }

    @Test
    public void testLeave() {
        managerService.createTicket(TICKET_ID1, EXPIRATION_DATE1);
        entranceService.visit(TICKET_ID1);
        entranceService.leave(TICKET_ID1);

        assertFalse(managerService.getInfo(TICKET_ID1).isHere());
        List<Event> events = eventStorage.getEvents(TICKET_ID1);
        assertThat(events, hasSize(3));
        assertThat(events, hasItems(
                equalTo(new CreateTicketEvent(TICKET_ID1, EXPIRATION_DATE1)),
                allOf(
                        instanceOf(VisitEvent.class),
                        hasProperty("ticketId", equalTo(TICKET_ID1))
                ),
                allOf(
                        instanceOf(LeaveEvent.class),
                        hasProperty("ticketId", equalTo(TICKET_ID1))
                )
        ));
    }

    @Test
    public void testVisitAfterExpiration() {
        managerService.createTicket(TICKET_ID1, EXPIRATION_DATE1);
        clock.plus(Duration.ofDays(10));
        entranceService.visit(TICKET_ID1);

        assertFalse(managerService.getInfo(TICKET_ID1).isHere());
        assertThat(eventStorage.getEvents(TICKET_ID1), not(hasItem(
                allOf(
                        instanceOf(VisitEvent.class),
                        hasProperty("ticketId", equalTo(TICKET_ID1))
                )
        )));
    }

    @Test
    public void testVisitAfterVisit() {
        managerService.createTicket(TICKET_ID1, EXPIRATION_DATE1);
        entranceService.visit(TICKET_ID1);
        entranceService.visit(TICKET_ID1);

        assertTrue(managerService.getInfo(TICKET_ID1).isHere());
        List<Event> events = eventStorage.getEvents(TICKET_ID1);
        assertThat(events, hasSize(2));
        assertThat(events, hasItems(
                equalTo(new CreateTicketEvent(TICKET_ID1, EXPIRATION_DATE1)),
                allOf(
                        instanceOf(VisitEvent.class),
                        hasProperty("ticketId", equalTo(TICKET_ID1))
                )
        ));
    }

    @Test
    public void testLeaveBeforeVisit() {
        managerService.createTicket(TICKET_ID1, EXPIRATION_DATE1);
        entranceService.leave(TICKET_ID1);

        assertFalse(managerService.getInfo(TICKET_ID1).isHere());
        assertThat(eventStorage.getEvents(TICKET_ID1), not(hasItem(
                allOf(
                        instanceOf(LeaveEvent.class),
                        hasProperty("ticketId", equalTo(TICKET_ID1))
                )
        )));
    }


}
