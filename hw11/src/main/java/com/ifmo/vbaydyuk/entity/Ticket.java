package com.ifmo.vbaydyuk.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ticket {
    private final int id;
    private LocalDateTime expirationTime;
    private final List<Visit> visits = new ArrayList<>();
    private LocalDateTime lastVisit;

    public Ticket(int id, LocalDateTime expirationTime) {
        this.id = id;
        this.expirationTime = expirationTime;
    }

    public void setLastVisit(LocalDateTime lastVisit) {
        this.lastVisit = lastVisit;
    }

    public LocalDateTime getLastVisit() {
        return lastVisit;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void addVisit(Visit visit) {
        visits.add(visit);
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isHere() {
        return lastVisit != null;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id &&
                Objects.equals(expirationTime, ticket.expirationTime) &&
                Objects.equals(visits, ticket.visits) &&
                Objects.equals(lastVisit, ticket.lastVisit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expirationTime, visits, lastVisit);
    }
}
