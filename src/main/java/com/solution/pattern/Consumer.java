package com.solution.pattern;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue<Message> queue;

    public Consumer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("Consumer started");
        try {
            while (!Thread.interrupted()) {
                Message message = queue.take();
                Thread.sleep(20);
                StringBuilder formattedMessage = new StringBuilder("Consumed: ");
                formattedMessage
                        .append(message.getId())
                        .append(" | ")
                        .append(message.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")))
                        .append(" | ")
                        .append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")))
                        .append(" | Queue size: ")
                        .append(queue.size());

                System.out.println(formattedMessage);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Consumer finished");
    }
}
