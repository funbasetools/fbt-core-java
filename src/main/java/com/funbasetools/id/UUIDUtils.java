package com.funbasetools.id;

import com.funbasetools.UtcDateUtils;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public final class UUIDUtils {

    /**
     * UUID Epoch is October 15, 1582 at midnight
     */
    private static final long UUID_EPOCH = computeUuidEpoch();

    private static final long MIN_CLOCK_SEQ_AND_NODE = 0x8080808080808080L;
    private static final long MAX_CLOCK_SEQ_AND_NODE = 0x7f7f7f7f7f7f7f7fL;

    private static final AtomicLong lastTimestamp = new AtomicLong(0);

    public static UUID nowTimeBased() {
        return timeBased(getCurrentTimestamp());
    }

    public static UUID timeBased(final long timestamp) {
        return new UUID(makeMostSignificantBytes(fromUnixTimestamp(timestamp)), MIN_CLOCK_SEQ_AND_NODE);
    }

    public static UUID startOf(final long timestamp) {
        return new UUID(makeMostSignificantBytes(fromUnixTimestamp(timestamp)), MIN_CLOCK_SEQ_AND_NODE);
    }

    public static UUID endOf(final long timestamp) {
        return new UUID(makeMostSignificantBytes(fromUnixTimestamp(timestamp)), MAX_CLOCK_SEQ_AND_NODE);
    }

    public static long unixTimestampOf(final UUID timeBasedUuid) {
        final long uuidTimestamp = timeBasedUuid.timestamp();
        return toUnixTimestamp(uuidTimestamp);
    }

    // private methods

    private static long toUnixTimestamp(final long timestamp) {
        return (timestamp / 10000) + UUID_EPOCH;
    }

    private static long fromUnixTimestamp(final long timestamp) {
        return (timestamp - UUID_EPOCH) * 10000;
    }

    private static long makeMostSignificantBytes(final long timestamp) {
        return (0x00000000ffffffffL & timestamp) << 32
             | (0x0000ffff00000000L & timestamp) >>> 16
             | (0xffff000000000000L & timestamp) >>> 48
             | 0x0000000000001000L;
    }

    private static long getCurrentTimestamp() {
        while (true) {
            final long now = UtcDateUtils.timestampNow();
            final long last = lastTimestamp.get();
            if (now > last) {
                if (lastTimestamp.compareAndSet(last, now)) {
                    return now;
                }
            }
            else {
                final long candidate = last + 1;
                if (lastTimestamp.compareAndSet(last, candidate)) {
                    return candidate;
                }
            }
        }
    }

    private static long computeUuidEpoch() {
        final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-0"));
        calendar.set(Calendar.YEAR, 1582);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }

    private UUIDUtils() {
    }
}
