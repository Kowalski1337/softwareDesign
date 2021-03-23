package com.ifmo.vbaydyuk.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Visit {
    private final LocalDateTime in;
    private final LocalDateTime out;

    public Visit(LocalDateTime in, LocalDateTime out) {
        this.in = in;
        this.out = out;
    }

    public long duration() {
        return out.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                - in.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
