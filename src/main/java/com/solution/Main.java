package com.solution;

import com.solution.pattern.Consumer;
import com.solution.pattern.Message;
import com.solution.pattern.Producer;
import com.solution.pattern.limiter.MessageRateLimiter;
import com.solution.pattern.limiter.RateLimiter;

import java.util.concurrent.*;

public class Main {

    static int MAX_QUEUE_CAPACITY = 100;
    static int RATE_LIMIT = 10;
    static int RATE_REFRESH_TIME_MS = 1000;
    static int EXECUTION_DURATION_SECONDS = 10;
    static int NUM_OF_PRODUCERS = 1;
    static int NUM_OF_CONSUMERS = 1;

    public static void main(String[] args) {
        BlockingQueue<Message> queue = new LinkedBlockingQueue<>(MAX_QUEUE_CAPACITY);
        RateLimiter rateLimiter = new MessageRateLimiter(RATE_LIMIT, RATE_REFRESH_TIME_MS);

        ExecutorService producerExecutor = Executors.newFixedThreadPool(NUM_OF_PRODUCERS);
        for (int i = 0; i < NUM_OF_PRODUCERS; i++) {
            producerExecutor.submit(new Producer(queue, rateLimiter));
        }

        ExecutorService consumerExecutor = Executors.newFixedThreadPool(NUM_OF_CONSUMERS);
        for (int i = 0; i < NUM_OF_CONSUMERS; i++) {
            consumerExecutor.submit(new Consumer(queue));
        }

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            producerExecutor.shutdownNow();
            consumerExecutor.shutdownNow();
            scheduler.shutdown();
        }, EXECUTION_DURATION_SECONDS, TimeUnit.SECONDS);
    }
}