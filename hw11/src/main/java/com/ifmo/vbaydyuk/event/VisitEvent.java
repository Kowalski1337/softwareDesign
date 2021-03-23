package com.ifmo.vbaydyuk.event;

import com.ifmo.vbaydyuk.storage.TicketStorage;

import java.time.LocalDateTime;
import java.util.Objects;

public class VisitEvent extends Event {
    private final LocalDateTime visitTime;

    public VisitEvent(int ticketId, LocalDateTime visitTime) {
        super(ticketId);
        this.visitTime = visitTime;
    }

    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    @Override
    protected void handleImpl(TicketStorage ticketStorage) {
        ticketStorage.visit(getTicketId(), visitTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        VisitEvent that = (VisitEvent) o;
        return Objects.equals(visitTime, that.visitTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), visitTime);
    }
}
