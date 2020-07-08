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

    @Test
    public void testHibernateCustomVersionOneTimestamp() {
        // given
        final UUID uuid = UUID.fromString("c0a86301-732f-1b81-8173-2fbb81bd0000");
        final long expectedTimestamp = 1594233684413L;


        // when
        final long timestamp = UUIDUtils.unixTimestampOfHibernateCustomVersionOne(uuid);

        // then
        assertEquals(expectedTimestamp, timestamp);
    }
}
