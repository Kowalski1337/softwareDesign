package com.ifmo.vbaydyuk.hw2;

import java.util.Date;

public class VKPost {
    private final String text;
    private final Date date;

    public VKPost(String text, Date date) {
        this.text = text;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

}
