package com.ifmo.vbaydyuk.service.clock;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

public class MutableClock extends Clock {
    private Clock delegate = Clock.systemDefaultZone();

    @Override
    public ZoneId getZone() {
        return ZoneId.systemDefault();
    }

    @Override
    public Clock withZone(ZoneId zoneId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Instant instant() {
        return delegate.instant();
    }

    public void plus(Duration duration) {
        delegate = Clock.offset(delegate, duration);
    }
}
