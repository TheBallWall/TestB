package com.solution.pattern;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    private final UUID id;
    private final LocalDateTime dateTime;

    public Message() {
        id = UUID.randomUUID();
        dateTime = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
