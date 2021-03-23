package com.ifmo.vbaydyuk.storage;

import com.ifmo.vbaydyuk.entity.Ticket;
import com.ifmo.vbaydyuk.entity.Visit;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TicketStorage {
    private final Map<Integer, Ticket> ticketInfoById = new HashMap<>();

    public void visit(int id, LocalDateTime visitTime) {
        getTicket(id).setLastVisit(visitTime);
    }

    public void leave(int id, LocalDateTime leaveTime) {
        Ticket ticket = getTicket(id);
        ticket.addVisit(new Visit(ticket.getLastVisit(), leaveTime));
        ticket.setLastVisit(null);
    }

    public void createTicket(int id, LocalDateTime expirationTime) {
        ticketInfoById.put(id, new Ticket(id, expirationTime));
    }

    public void extendTicket(int id, LocalDateTime expirationTime) {
        getTicket(id).setExpirationTime(expirationTime);
    }

    public Ticket getTicket(int id) {
        Ticket ticket = ticketInfoById.get(id);
        if (ticket == null) {
            throw new IllegalArgumentException("No such ticket");
        }
        return ticket;
    }

    public Collection<Ticket> getTickets() {
        return ticketInfoById.values();
    }
}
