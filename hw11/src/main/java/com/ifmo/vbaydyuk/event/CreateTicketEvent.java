package com.ifmo.vbaydyuk.event;

import com.ifmo.vbaydyuk.storage.TicketStorage;

import java.time.LocalDateTime;
import java.util.Objects;

public class CreateTicketEvent extends Event {
    private final LocalDateTime expirationTime;

    public CreateTicketEvent(int ticketId, LocalDateTime expirationTime) {
        super(ticketId);
        this.expirationTime = expirationTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    @Override
    protected void handleImpl(TicketStorage ticketStorage) {
        ticketStorage.createTicket(getTicketId(), expirationTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CreateTicketEvent that = (CreateTicketEvent) o;
        return Objects.equals(expirationTime, that.expirationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), expirationTime);
    }
}
