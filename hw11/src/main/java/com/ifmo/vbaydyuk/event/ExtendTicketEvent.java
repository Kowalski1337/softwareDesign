package com.ifmo.vbaydyuk.event;

import com.ifmo.vbaydyuk.storage.TicketStorage;

import java.time.LocalDateTime;
import java.util.Objects;

public class ExtendTicketEvent extends Event {
    private final LocalDateTime expirationTime;

    public ExtendTicketEvent(int ticketId, LocalDateTime expirationTime) {
        super(ticketId);
        this.expirationTime = expirationTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    @Override
    protected void handleImpl(TicketStorage ticketStorage) {
        ticketStorage.extendTicket(getTicketId(), expirationTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ExtendTicketEvent that = (ExtendTicketEvent) o;
        return Objects.equals(expirationTime, that.expirationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), expirationTime);
    }
}
