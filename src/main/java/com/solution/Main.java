package com.solution;

import com.solution.pattern.Consumer;
import com.solution.pattern.MessageQueue;
import com.solution.pattern.Producer;
import com.solution.pattern.limiter.MessageRateLimiter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Main {

    static int MAX_QUEUE_CAPACITY = 100;
    static int RATE_LIMIT = 100;
    static int RATE_REFRESH_TIME_MS = 1000;
    static int EXECUTION_DURATION_SECONDS = 3;
    static int NUM_OF_PRODUCERS = 2;
    static int NUM_OF_CONSUMERS = 5;

    public static void main(String[] args) {
        LocalTime startTime = LocalTime.now();

        MessageQueue messageQueue = new MessageQueue(MAX_QUEUE_CAPACITY);

        MessageRateLimiter messageRateLimiter = new MessageRateLimiter(RATE_LIMIT,RATE_REFRESH_TIME_MS);

        List<Producer> producerList = new ArrayList<>();
        for(int i = 0; i< NUM_OF_PRODUCERS; i++){
            Producer producer = new Producer(messageQueue, messageRateLimiter);
            Thread producerThread = new Thread(producer);
            producerThread.start();
            producerList.add(producer);
        }

        List<Consumer> consumerList = new ArrayList<>();
        for(int i = 0; i< NUM_OF_CONSUMERS; i++){
            Consumer consumer = new Consumer(messageQueue);
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();
            consumerList.add(consumer);
        }

        while (startTime.until(LocalTime.now(), ChronoUnit.SECONDS) < EXECUTION_DURATION_SECONDS) {}

        producerList.forEach(Producer::stop);
        consumerList.forEach(Consumer::stop);

    }
}
