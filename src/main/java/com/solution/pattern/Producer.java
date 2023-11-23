package com.solution.pattern;

import com.solution.pattern.limiter.RateLimiter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;


public class Producer implements Runnable {
    private final BlockingQueue<Message> queue;
    private final RateLimiter rateLimiter;

    public Producer(BlockingQueue<Message> queue, RateLimiter rateLimiter) {
        this.queue = queue;
        this.rateLimiter = rateLimiter;
    }

    @Override
    public void run() {
        System.out.println("Producer started");
        try {
            while (!Thread.interrupted()) {
                if (rateLimiter.acquire()) {

                    // Simulate producer time delay between requests
                    Thread.sleep(ThreadLocalRandom.current().nextInt(10,20));

                    queue.put(new Message());
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Producer finished");
    }

}
