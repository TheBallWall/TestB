package com.solution.pattern;

import com.solution.pattern.limiter.MessageRateLimiter;

public class Producer implements Runnable {
    private final MessageQueue queue;

    private final MessageRateLimiter rateLimiter;
    private boolean running = false;

    public Producer(MessageQueue queue, MessageRateLimiter rateLimiter) {
        this.queue = queue;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public void run() {
        running = true;
        produce();
    }

    public void produce() {
        System.out.println("Producer started");
        while (running) {
            if (rateLimiter.acquire()) {
                try {
                    Thread.sleep(10);
                    queue.add(new Message());
                } catch (InterruptedException e) {
                    System.out.println("Error: Producer - creating");
                    System.out.println(new RuntimeException(e));
                    break;
                }
            }
        }
        System.out.println("Producer finished");
    }

    public void stop() {
        running = false;
        //queue.notifyProducerBlock();
    }
}
