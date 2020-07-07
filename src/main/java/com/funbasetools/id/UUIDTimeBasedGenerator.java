package com.funbasetools.id;

import com.funbasetools.BytesUtil;
import com.funbasetools.UtcDateUtils;
import com.funbasetools.network.NetUtil;
import java.util.Optional;
import java.util.UUID;

public class UUIDTimeBasedGenerator implements UUIDGenerator {

    private final static byte[] jvmIdentifierBytes = getJvmIdentifierBytes();
    private final static long leastSignificantBits = getLeastSignificantBits();

    public UUIDTimeBasedGenerator() {
    }

    @Override
    public int getGeneratedVersion() {
        return 1;
    }

    @Override
    public UUID generate(final Long timestamp) {
        final UUID timeBased = Optional.ofNullable(timestamp)
            .map(UUIDUtils::timeBased)
            .orElseGet(UUIDUtils::nowTimeBased);

        return new UUID(timeBased.getMostSignificantBits(), leastSignificantBits);
    }

    @Override
    public UUID generate() {
        return generate(null);
    }

    private static byte[] getJvmIdentifierBytes() {
        return BytesUtil.toByteArray((int) (UtcDateUtils.timestampNow() >>> 8));
    }

    private static long getLeastSignificantBits() {
        final byte[] hiBits = new byte[8];
        System.arraycopy(NetUtil.getAddressBytes(), 0, hiBits, 0, 4);
        System.arraycopy(jvmIdentifierBytes, 0, hiBits, 4, 4);
        hiBits[6] = (byte)(hiBits[6] & 15);
        hiBits[6] = (byte)(hiBits[6] | 16);

        return BytesUtil.asLong(hiBits);
    }
}
