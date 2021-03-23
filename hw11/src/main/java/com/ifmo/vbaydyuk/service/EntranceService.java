package com.ifmo.vbaydyuk.service;

import com.ifmo.vbaydyuk.entity.Ticket;
import com.ifmo.vbaydyuk.event.LeaveEvent;
import com.ifmo.vbaydyuk.event.VisitEvent;
import com.ifmo.vbaydyuk.storage.EventStorage;
import com.ifmo.vbaydyuk.storage.TicketStorage;

import java.time.Clock;
import java.time.LocalDateTime;

public class EntranceService {
    private final EventStorage eventStorage;
    private final TicketStorage ticketStorage;
    private final Clock clock;

    public EntranceService(EventStorage eventStorage, TicketStorage ticketStorage, Clock clock) {
        this.eventStorage = eventStorage;
        this.ticketStorage = ticketStorage;
        this.clock = clock;
    }

    public void visit(int id) {
        LocalDateTime time = LocalDateTime.ofInstant(clock.instant(), clock.getZone());
        if (canVisit(id, time)) {
            new VisitEvent(id, time).handle(eventStorage, ticketStorage);
        }
    }

    public void leave(int id) {
        LocalDateTime time = LocalDateTime.ofInstant(clock.instant(), clock.getZone());
        if (canLeave(id)) {
            new LeaveEvent(id, time).handle(eventStorage, ticketStorage);
        }
    }

    private boolean canVisit(int id, LocalDateTime time) {
        Ticket ticket = ticketStorage.getTicket(id);
        return !ticket.isHere() && ticket.getExpirationTime().isAfter(time);
    }

    private boolean canLeave(int id) {
        return ticketStorage.getTicket(id).isHere();
    }
}
