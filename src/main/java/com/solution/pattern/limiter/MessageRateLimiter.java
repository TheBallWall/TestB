package com.solution.pattern.limiter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageRateLimiter implements RateLimiter {

    private final int rateLimit;
    private final int rateRefreshTime;
    private AtomicInteger currentNumberOfTokens;
    private LocalDateTime lastRefreshTime;
    private LocalDateTime nextRefreshTime;

    public MessageRateLimiter(int rateLimit, int rateRefreshTime) {
        this.rateLimit = rateLimit;
        this.rateRefreshTime = rateRefreshTime;
        this.nextRefreshTime = LocalDateTime.now();
        this.currentNumberOfTokens = new AtomicInteger(0);
        refresh();
    }

    @Override
    public boolean acquire() {
        refresh();
//        if(currentNumberOfTokens.get() > 0){
//            currentNumberOfTokens.decrementAndGet();
//            return true;
//        }
        return currentNumberOfTokens.getAndDecrement() > 0;
    }

    private void refresh(){
        if(LocalDateTime.now().isAfter(nextRefreshTime)){
            currentNumberOfTokens.set(rateLimit);
            lastRefreshTime = LocalDateTime.now();
            nextRefreshTime = lastRefreshTime.plus(rateRefreshTime, ChronoUnit.MILLIS);
        }
    }
}
