package com.ifmo.vbaydyuk.service;

import com.ifmo.vbaydyuk.entity.Ticket;
import com.ifmo.vbaydyuk.event.CreateTicketEvent;
import com.ifmo.vbaydyuk.event.ExtendTicketEvent;
import com.ifmo.vbaydyuk.storage.EventStorage;
import com.ifmo.vbaydyuk.storage.TicketStorage;

import java.time.LocalDateTime;
import java.util.Collection;

public class ManagerService {
    private final EventStorage eventStorage;
    private final TicketStorage ticketStorage;

    public ManagerService(EventStorage eventStorage, TicketStorage ticketStorage) {
        this.eventStorage = eventStorage;
        this.ticketStorage = ticketStorage;
    }

    public void createTicket(int id, LocalDateTime expirationTime) {
        new CreateTicketEvent(id, expirationTime).handle(eventStorage, ticketStorage);
    }

    public void extendTicket(int id, LocalDateTime expirationTime) {
        new ExtendTicketEvent(id, expirationTime).handle(eventStorage, ticketStorage);
    }

    public Ticket getInfo(int id) {
        return ticketStorage.getTicket(id);
    }

    public Collection<Ticket> getAllInfo() {
        return ticketStorage.getTickets();
    }
}
