package com.solution.pattern;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

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

                // Simulate consumer work load
                Thread.sleep(ThreadLocalRandom.current().nextInt(20,30));

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
