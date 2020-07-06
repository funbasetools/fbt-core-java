package com.funbasetools.id;

import static org.junit.Assert.assertEquals;

import java.util.UUID;
import org.junit.Test;

public class UUIDTimeBasedGeneratorTest {

    @Test
    public void testTimedBasedUUIDVersion() {
        // given
        final UUID uuid = new UUIDTimeBasedGenerator().generate();

        // then
        assertEquals(1, uuid.version());
    }

    @Test
    public void testTimeBasedUUIDGeneration() {
        // given
        final long timestampNow = System.currentTimeMillis();
        final UUID uuid = new UUIDTimeBasedGenerator().generate(timestampNow);

        // when
        final long timestamp = UUIDUtils.unixTimestampOf(uuid);

        // then
        assertEquals(1, uuid.version());
        assertEquals(timestampNow, timestamp);
    }
}
