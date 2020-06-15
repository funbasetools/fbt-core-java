package com.funbasetools;

import java.time.*;
import java.time.temporal.ChronoUnit;

public final class UtcDateUtils {

    private UtcDateUtils() {
    }

    public static LocalDateTime now() {
        return LocalDateTime.now(Clock.systemUTC());
    }

    public static Instant instantNow() {
        return toInstant(now());
    }

    public static long timestampNow() {
        return instantNow().toEpochMilli();
    }

    public static LocalDateTime today() {
        return now().truncatedTo(ChronoUnit.DAYS);
    }

    public static long timestampToday() {
        return toInstant(today()).toEpochMilli();
    }

    public static LocalDateTime previousMidnight(final LocalDateTime localDateTime) {
        return localDateTime
            .truncatedTo(ChronoUnit.DAYS);
    }

    public static long previousMidnightTimestamp(final long timestamp) {
        return Instant
            .ofEpochMilli(timestamp)
            .truncatedTo(ChronoUnit.DAYS)
            .toEpochMilli();
    }

    public static LocalDateTime nextMidnight(final LocalDateTime localDateTime) {
        return localDateTime
            .plus(Duration.ofDays(1))
            .truncatedTo(ChronoUnit.DAYS);
    }

    public static long nextMidnightTimestamp(final long timestamp) {
        return Instant
            .ofEpochMilli(timestamp)
            .plus(Duration.ofDays(1))
            .toEpochMilli();
    }

    public static int getZoneOffsetInSeconds() {
        return ZonedDateTime.now(Clock.systemDefaultZone()).getOffset().getTotalSeconds();
    }

    // private methods

    private static Instant toInstant(final LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.UTC);
    }
}
