package com.ifmo.vbaydyuk.service;

import com.ifmo.vbaydyuk.storage.EventStorage;
import com.ifmo.vbaydyuk.storage.TicketStorage;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDateTime;

public class ServiceTestBase {
    protected EventStorage eventStorage;
    protected TicketStorage ticketStorage;
    protected ManagerService managerService;
    protected static final int TICKET_ID1 = 1;
    protected static final int TICKET_ID2 = 2;
    protected static final int TICKET_ID3 = 3;
    protected static final LocalDateTime NOW = LocalDateTime.now();
    protected static final LocalDateTime EXPIRATION_DATE1 = NOW.plusDays(3);
    protected static final LocalDateTime EXPIRATION_DATE2 = NOW.plusDays(5);

    @BeforeEach
    void setUp() {
        eventStorage = new EventStorage();
        ticketStorage = new TicketStorage();
        managerService = new ManagerService(eventStorage, ticketStorage);
    }
}
