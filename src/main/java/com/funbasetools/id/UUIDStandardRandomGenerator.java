package com.funbasetools.id;

import java.time.Instant;
import java.util.UUID;

public class UUIDStandardRandomGenerator implements UUIDGenerator {

    @Override
    public int getGeneratedVersion() {
        return 4;
    }

    @Override
    public UUID generate(final Instant instant) {
        return UUID.randomUUID();
    }

    @Override
    public UUID generate() {
        return generate(null);
    }
}
