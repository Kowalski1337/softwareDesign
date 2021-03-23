package com.ifmo.vbaydyuk.service;

import com.ifmo.vbaydyuk.entity.Ticket;
import com.ifmo.vbaydyuk.event.CreateTicketEvent;
import com.ifmo.vbaydyuk.event.ExtendTicketEvent;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ManagerServiceTest extends ServiceTestBase {

    @Test
    public void testEmpty() {
        assertThrows(IllegalArgumentException.class, () -> managerService.getInfo(TICKET_ID1));
        assertThat(managerService.getAllInfo(), empty());
    }

    @Test
    public void testUpdatesAfterCreation() {
        managerService.createTicket(TICKET_ID1, EXPIRATION_DATE1);
        managerService.createTicket(TICKET_ID2, EXPIRATION_DATE1);
        managerService.createTicket(TICKET_ID3, EXPIRATION_DATE1);

        assertThat(managerService.getAllInfo(), contains(
                new Ticket(TICKET_ID1, EXPIRATION_DATE1),
                new Ticket(TICKET_ID2, EXPIRATION_DATE1),
                new Ticket(TICKET_ID3, EXPIRATION_DATE1)
        ));

        assertThat(eventStorage.getEventsByTicket(), allOf(
                hasEntry(TICKET_ID1, Collections.singletonList(new CreateTicketEvent(TICKET_ID1, EXPIRATION_DATE1))),
                hasEntry(TICKET_ID2, Collections.singletonList(new CreateTicketEvent(TICKET_ID2, EXPIRATION_DATE1))),
                hasEntry(TICKET_ID3, Collections.singletonList(new CreateTicketEvent(TICKET_ID3, EXPIRATION_DATE1)))
        ));
    }

    @Test
    public void testUpdatesAfterExtension() {
        managerService.createTicket(TICKET_ID1, EXPIRATION_DATE1);
        managerService.createTicket(TICKET_ID2, EXPIRATION_DATE1);
        managerService.createTicket(TICKET_ID3, EXPIRATION_DATE1);

        managerService.extendTicket(TICKET_ID1, EXPIRATION_DATE2);
        managerService.extendTicket(TICKET_ID3, EXPIRATION_DATE2);

        assertThat(managerService.getAllInfo(), contains(
                new Ticket(TICKET_ID1, EXPIRATION_DATE2),
                new Ticket(TICKET_ID2, EXPIRATION_DATE1),
                new Ticket(TICKET_ID3, EXPIRATION_DATE2)
        ));

        assertThat(eventStorage.getEventsByTicket(), allOf(
                hasEntry(TICKET_ID1, Arrays.asList(new CreateTicketEvent(TICKET_ID1, EXPIRATION_DATE1), new ExtendTicketEvent(TICKET_ID1, EXPIRATION_DATE2))),
                hasEntry(TICKET_ID3, Arrays.asList(new CreateTicketEvent(TICKET_ID3, EXPIRATION_DATE1), new ExtendTicketEvent(TICKET_ID3, EXPIRATION_DATE2)))
        ));

        assertThat(eventStorage.getEventsByTicket(), hasEntry(TICKET_ID2, Collections.singletonList(new CreateTicketEvent(TICKET_ID2, EXPIRATION_DATE1))));
    }
}
