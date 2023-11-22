package com.solution.pattern;

import com.solution.pattern.limiter.RateLimiter;

import java.util.concurrent.BlockingQueue;


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
                    Message message = new Message();
                    queue.put(message);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Producer finished");
    }

}
