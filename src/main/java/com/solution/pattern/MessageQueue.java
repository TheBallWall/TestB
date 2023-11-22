package com.solution.pattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    public final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private final int maxSize;
    private final Object PRODUCER_BLOCK = new Object(); // blocks producer threads when queue is full
    private final Object CONSUMER_BLOCK = new Object(); // blocks consumer threads when queue is empty

    public MessageQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void add(Message message) throws InterruptedException {
        queue.put(message);
        //notifyConsumerBlock();
    }

    public Message remove() throws InterruptedException {
        Message message = queue.take();
        //if(queue.size() < maxSize) notifyProducerBlock();
        return message;
    }

    public boolean isFull(){
        return queue.size() >= maxSize;
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public int getCurrentQueueSize(){
        return queue.size();
    }

//    public void waitProducerBlock() throws InterruptedException {
//        synchronized (PRODUCER_BLOCK){
//            PRODUCER_BLOCK.wait();
//        }
//    }
//    public void notifyProducerBlock(){
//        synchronized (PRODUCER_BLOCK){
//            PRODUCER_BLOCK.notify();
//        }
//    }
//    public void waitConsumerBlock() throws InterruptedException {
//        synchronized (CONSUMER_BLOCK){
//            CONSUMER_BLOCK.wait();
//        }
//    }
//
//    public void notifyConsumerBlock(){
//        synchronized (CONSUMER_BLOCK){
//            CONSUMER_BLOCK.notify();
//        }
//    }
}
