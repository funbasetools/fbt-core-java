package com.funbasetools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.Test;

public class UtcDateUtilsTest {

    @Test
    public void testUtcNow() {
        // given
        final Clock localClock = Clock.systemDefaultZone();
        final int offsetInSeconds = UtcDateUtils.getZoneOffsetInSeconds();

        // when
        final LocalDateTime utcNow = UtcDateUtils.now();
        final LocalDateTime localNow = LocalDateTime.now(localClock);
        final LocalDateTime localToUtc = localNow.minus(offsetInSeconds, ChronoUnit.SECONDS);

        // then
        final long differenceInMillis = Math.abs(Duration.between(utcNow, localToUtc).toMillis());

        assertTrue(differenceInMillis < 10);
    }

    @Test
    public void testToday() {
        // when
        final LocalDateTime today = UtcDateUtils.today();

        // then
        assertEquals(0, today.getHour());
        assertEquals(0, today.getMinute());
        assertEquals(0, today.getSecond());
        assertEquals(0, today.getNano());
    }

    @Test
    public void testPreviousMidnight() {
        // given
        final LocalDateTime localDateTime = LocalDateTime
            .of(2020, 7, 15, 14, 35, 12);

        // when
        final LocalDateTime previousMidnight = UtcDateUtils.previousMidnight(localDateTime);

        // then
        assertEquals(
            LocalDateTime.of(2020, 7, 15, 0, 0, 0),
            previousMidnight
        );
    }

    @Test
    public void testNextMidnight() {
        // given
        final LocalDateTime localDateTime = LocalDateTime
            .of(2020, 7, 15, 14, 35, 12);

        // when
        final LocalDateTime nextMidnight = UtcDateUtils.nextMidnight(localDateTime);

        // then
        assertEquals(
            LocalDateTime.of(2020, 7, 16, 0, 0, 0),
            nextMidnight
        );
    }
}
