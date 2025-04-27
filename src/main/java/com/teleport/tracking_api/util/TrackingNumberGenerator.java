package com.teleport.tracking_api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Generator for unique tracking numbers using a Snowflake-like algorithm.
 */
@Component
public class TrackingNumberGenerator {
	
	Logger logger = LoggerFactory.getLogger(TrackingNumberGenerator.class);
    
	// In a horizontally scaled setup, this workerId should be unique per instance.
	@Value("${tracking.worker-id:0}")
    private long workerId;
    private static final long EPOCH = 1577836800000L; // January 1, 2020;
    private static final long WORKER_ID_BITS = 10L;
    private static final long SEQUENCE_BITS = 12L;
    private static final long MAX_WORKER_ID = (1L << WORKER_ID_BITS) - 1;
    private static final long MAX_SEQUENCE = (1L << SEQUENCE_BITS) - 1;
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    private long lastTimestamp = -1L;
    private long sequence = 0L;
    private final Lock lock = new ReentrantLock();

    /**
     * Generates the next unique tracking number.
     */
    public String nextTrackingNumber() {
    	if (workerId < 0 || workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException(String.format(
                    "Worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
    	logger.info("Worked ID currently used for generation is {} ", workerId);
        lock.lock();
        try {
            long currentTimestamp = System.currentTimeMillis();
            if (currentTimestamp < lastTimestamp) {
                throw new IllegalStateException("Clock moved backwards. Refusing to generate id.");
            }
            if (currentTimestamp == lastTimestamp) {
                sequence = (sequence + 1) & MAX_SEQUENCE;
                if (sequence == 0) {
                    currentTimestamp = waitNextMillis(currentTimestamp);
                }
            } else {
                sequence = 0;
            }

            lastTimestamp = currentTimestamp;

            long id = ((currentTimestamp - EPOCH) << TIMESTAMP_SHIFT)
                    | (workerId << WORKER_ID_SHIFT)
                    | sequence;
            // Convert the unique id to a base-36 uppercase string
            return Long.toString(id, 36).toUpperCase();
        } finally {
            lock.unlock();
        }
    }

    private long waitNextMillis(long timestamp) {
        long newTimestamp = System.currentTimeMillis();
        while (newTimestamp <= lastTimestamp) {
            newTimestamp = System.currentTimeMillis();
        }
        return newTimestamp;
    }
}