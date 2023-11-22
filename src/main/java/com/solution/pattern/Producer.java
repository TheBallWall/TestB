package com.solution.pattern;

import java.time.LocalDateTime;

public class Producer implements Runnable {
    private final MessageQueue queue;
    private boolean running = false;

    public Producer(MessageQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        running = true;
        produce();
    }

    public void produce() {
        System.out.println("Producer started");
        while (running) {
            if (queue.isFull()) {
                try {
                    queue.waitIsNotFull();
                } catch (InterruptedException e) {
                    System.out.println("Error: Producer - waiting");
                    System.out.println(new RuntimeException(e));
                    break;
                }
            }
            if (!running) {
                break;
            }
            try {
                Thread.sleep(10);
                queue.add(new Message());
            } catch (InterruptedException e) {
                System.out.println("Error: Producer - creating");
                System.out.println(new RuntimeException(e));
                break;
            }
        }
        System.out.println("Producer finished");
    }

    public void stop() {
        running = false;
        queue.notifyIsNotFull();
    }
}
