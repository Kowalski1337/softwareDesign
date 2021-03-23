package com.ifmo.vbaydyuk.service;

import com.ifmo.vbaydyuk.entity.Ticket;
import com.ifmo.vbaydyuk.entity.Visit;
import com.ifmo.vbaydyuk.event.VisitEvent;
import com.ifmo.vbaydyuk.storage.EventStorage;
import com.ifmo.vbaydyuk.storage.TicketStorage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.UnaryOperator.identity;

public class StatisticsService {
    private final EventStorage eventStorage;
    private final TicketStorage ticketStorage;

    public StatisticsService(EventStorage eventStorage, TicketStorage ticketStorage) {
        this.eventStorage = eventStorage;
        this.ticketStorage = ticketStorage;
    }

    public Map<LocalDate, Integer> visitPerDate() {
        return eventStorage.getEventsByTicket().values()
                .stream()
                .flatMap(Collection::stream)
                .filter(event -> event instanceof VisitEvent)
                .map(event -> (VisitEvent) event)
                .map(VisitEvent::getVisitTime)
                .map(LocalDateTime::toLocalDate)
                .collect(Collectors.toMap(
                        identity(),
                        date -> 1,
                        Integer::sum
                ));
    }

    public double averageFreq() {
        Map<LocalDate, Integer> visitPerDate = visitPerDate();
        return (double) visitPerDate.values().stream()
                .reduce(0, Integer::sum) / visitPerDate.size();
    }

    public double averageVisitTime() {
        return (double) ticketStorage.getTickets()
                .stream()
                .map(Ticket::getVisits)
                .flatMap(Collection::stream)
                .map(Visit::duration)
                .reduce(0L, Long::sum) /
                ticketStorage.getTickets()
                        .stream()
                        .map(Ticket::getVisits)
                        .mapToLong(Collection::size)
                        .sum();
    }

}
