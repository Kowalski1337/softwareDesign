package com.ifmo.vbaydyuk.event;

import com.ifmo.vbaydyuk.storage.TicketStorage;

import java.time.LocalDateTime;
import java.util.Objects;

public class LeaveEvent extends Event {
    private final LocalDateTime leaveTime;

    public LeaveEvent(int ticketId, LocalDateTime leaveTime) {
        super(ticketId);
        this.leaveTime = leaveTime;
    }

    public LocalDateTime getLeaveTime() {
        return leaveTime;
    }

    @Override
    protected void handleImpl(TicketStorage ticketStorage) {
        ticketStorage.leave(getTicketId(), leaveTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LeaveEvent that = (LeaveEvent) o;
        return Objects.equals(leaveTime, that.leaveTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), leaveTime);
    }
}
