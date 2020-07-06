package com.funbasetools.id;

import static org.junit.Assert.assertEquals;

import java.util.UUID;
import org.junit.Test;

public class UUIDUtilsTest {

    @Test
    public void testTimeBasedUUIDVersion() {
        // given
        final long time = System.currentTimeMillis();

        // when
        final UUID uuid = UUIDUtils.timeBased(time);

        // then
        assertEquals(1, uuid.version());
    }

    @Test
    public void testTimeBasedUUIDTimestampOf() {
        // given
        final long time = System.currentTimeMillis();
        final UUID uuid = UUIDUtils.timeBased(time);

        // when
        final long timestamp = UUIDUtils.unixTimestampOf(uuid);

        // then
        assertEquals(time, timestamp);
    }
}
