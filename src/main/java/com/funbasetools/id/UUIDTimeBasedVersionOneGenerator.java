package com.funbasetools.id;

import com.funbasetools.BytesUtil;
import com.funbasetools.Try;
import com.funbasetools.UtcDateUtils;
import com.funbasetools.network.NetUtil;
import java.time.Instant;
import java.util.UUID;

public class UUIDTimeBasedVersionOneGenerator implements UUIDGenerator {

    private static short counter;
    private static byte[] jvmIdentifierBytes;
    private final long mostSignificantBits;

    public UUIDTimeBasedVersionOneGenerator() {
        this.mostSignificantBits =
            Try.of(() -> {
                final byte[] hiBits = new byte[8];
                System.arraycopy(NetUtil.getAddressBytes(), 0, hiBits, 0, 4);
                System.arraycopy(getJvmIdentifierBytes(), 0, hiBits, 4, 4);
                hiBits[6] = (byte)(hiBits[6] & 15);
                hiBits[6] = (byte)(hiBits[6] | 16);

                return BytesUtil.asLong(hiBits);
            })
            .toOptional()
            .orElse(0L);
    }

    @Override
    public int getGeneratedVersion() {
        return 1;
    }

    @Override
    public UUID generate(Instant instant) {
        final long leastSignificantBits = generateLeastSignificantBits(instant.toEpochMilli());
        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    private static long generateLeastSignificantBits(final long seed) {
        final byte[] loBits = new byte[8];
        final short hiTime = (short)((int)(seed >>> 32));
        final int loTime = (int)seed;
        System.arraycopy(BytesUtil.toByteArray(hiTime), 0, loBits, 0, 2);
        System.arraycopy(BytesUtil.toByteArray(loTime), 0, loBits, 2, 4);
        System.arraycopy(BytesUtil.toByteArray(getCounter()), 0, loBits, 6, 2);
        loBits[0] = (byte)(loBits[0] & 63);
        loBits[0] = (byte)(loBits[0] | 128);

        return BytesUtil.asLong(loBits);
    }

    private static byte[] getJvmIdentifierBytes() {
        if (jvmIdentifierBytes == null) {
            jvmIdentifierBytes = BytesUtil.toByteArray((int) (UtcDateUtils.timestampNow() >>> 8));
        }

        return jvmIdentifierBytes;
    }

    private static short getCounter() {
        synchronized (UUIDTimeBasedVersionOneGenerator.class) {
            if (counter < 0) {
                counter = 0;
            }
            final short res = counter;
            counter = (short)(counter + 1);
            return res;
        }
    }
}
