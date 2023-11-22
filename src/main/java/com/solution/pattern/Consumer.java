package com.solution.pattern;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Consumer implements Runnable {
    private MessageQueue queue;
    private boolean running = false;

    public Consumer(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        running = true;
        consume();
    }

    public void consume() {
        System.out.println("Consumer started");
        while (running) {
            if (queue.isEmpty()) {
                try {
                    queue.waitIsNotEmpty();
                } catch (InterruptedException e) {
                    System.out.println("Error: Consumer - waiting");
                    System.out.println(new RuntimeException(e));
                    break;
                }
            }
            if (!running) {
                break;
            }
            Message message = queue.remove();
            try {
                Thread.sleep(20);
                StringBuilder formattedMessage = new StringBuilder("Consumed: ");
                formattedMessage
                        .append(message.getId())
                        .append(" | ")
                        .append(message.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")))
                        .append(" | ")
                        .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")))
                        .append(" | Queue size: ")
                        .append(queue.getCurrentQueueSize());

                System.out.println(formattedMessage);
            } catch (InterruptedException e) {
                System.out.println("Error: Consumer - processing");
                System.out.println(new RuntimeException(e));
                break;
            }
        }
        System.out.println("Consumer finished");
    }

    public void stop() {
        running = false;
        queue.notifyIsNotEmpty();
    }
}
