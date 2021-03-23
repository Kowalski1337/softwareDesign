package com.ifmo.vbaydyuk.event;

import com.ifmo.vbaydyuk.storage.EventStorage;
import com.ifmo.vbaydyuk.storage.TicketStorage;

import java.util.Objects;

public abstract class Event {
    private final int ticketId;

    public Event(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void handle(EventStorage eventStorage, TicketStorage ticketStorage) {
        eventStorage.addEvent(this);
        handleImpl(ticketStorage);
    }

    protected abstract void handleImpl(TicketStorage ticketStorage);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return ticketId == event.ticketId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId);
    }
}
