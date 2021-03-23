package com.ifmo.vbaydyuk.storage;

import com.ifmo.vbaydyuk.event.Event;
import org.apache.commons.collections.ListUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

public class EventStorage {
    private final Map<Integer, List<Event>> eventsByTicket = new HashMap<>();

    public void addEvent(Event event) {
        eventsByTicket.merge(event.getTicketId(), singletonList(event), ListUtils::union);
    }

    public Map<Integer, List<Event>> getEventsByTicket() {
        return eventsByTicket;
    }

    public List<Event> getEvents(int id) {
        return eventsByTicket.get(id);
    }
}
