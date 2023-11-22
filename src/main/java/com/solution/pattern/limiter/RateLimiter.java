package com.solution.pattern.limiter;

public interface RateLimiter {
    boolean acquire();
}
