package com.solution.pattern;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {
    private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
    private final int maxSize;
    private final Object IS_NOT_FULL = new Object();
    private final Object IS_NOT_EMPTY = new Object();

    public MessageQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public void add(Message message){
        queue.add(message);
        notifyIsNotEmpty();
    }

    public Message remove(){
        Message message = queue.poll();
        notifyIsNotFull();
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

    public void waitIsNotFull() throws InterruptedException {
        synchronized (IS_NOT_FULL){
            IS_NOT_FULL.wait();
        }
    }
    public void notifyIsNotFull(){
        synchronized (IS_NOT_FULL){
            IS_NOT_FULL.notify();
        }
    }
    public void waitIsNotEmpty() throws InterruptedException {
        synchronized (IS_NOT_EMPTY){
            IS_NOT_EMPTY.wait();
        }
    }

    public void notifyIsNotEmpty(){
        synchronized (IS_NOT_EMPTY){
            IS_NOT_EMPTY.notify();
        }
    }
}
