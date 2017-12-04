package com.tingcore.cdc.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author palmithor
 * @since 2017-10-19
 */
public class CommonDataUtils {

    private final static AtomicLong ID_GENERATOR = new AtomicLong(2000);

    public static Long getNextId() {
        return ID_GENERATOR.incrementAndGet();
    }

    public static String nextZeroPaddedId(final Integer length) {
        return StringUtils.leftPad(String.valueOf(CommonDataUtils.getNextId()), length, '0');
    }

    public static Long getRandomPastTimestamp() {
        return Instant.now().minus(ThreadLocalRandom.current().nextLong(100), ChronoUnit.DAYS).toEpochMilli();
    }
}
