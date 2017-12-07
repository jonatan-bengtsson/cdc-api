package com.tingcore.cdc.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author palmithor
 * @since 2017-10-19
 */
public class CommonDataUtils {

    private static final Random rand = new Random();
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

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String randomEmail() {
        return String.format("%s@email.com", randomUUID());
    }

    public static String randomNumberStr(final int min, final int max) {
        return String.valueOf(randomLong(min, max));
    }

    public static Long randomLong(final int min, final int max) {
        return (long) (rand.nextInt((max - min) + 1) + min);
    }
}
