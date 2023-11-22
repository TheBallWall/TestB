package com.solution.pattern.limiter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MessageRateLimiter implements RateLimiter {

    private final int rateLimit;
    private final int rateRefreshTime;
    private int currentNumberOfTokens;
    private LocalDateTime lastRefreshTime;
    private LocalDateTime nextRefreshTime;

    public MessageRateLimiter(int rateLimit, int rateRefreshTime) {
        this.rateLimit = rateLimit;
        this.rateRefreshTime = rateRefreshTime;
        this.nextRefreshTime = LocalDateTime.now();
        refresh();
    }

    @Override
    public boolean acquire() {

        return false;
    }

    private void refresh(){
        if(LocalDateTime.now().isAfter(nextRefreshTime)){
            currentNumberOfTokens = rateLimit;
            lastRefreshTime = LocalDateTime.now();
            nextRefreshTime = lastRefreshTime.plus(rateRefreshTime, ChronoUnit.MILLIS);
        }
    }
}
