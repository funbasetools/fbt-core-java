package com.funbasetools.id;

import java.util.UUID;

public class UUIDStandardRandomGenerator implements UUIDGenerator {

    @Override
    public int getGeneratedVersion() {
        return 4;
    }

    @Override
    public UUID generate(final Long timestamp) {
        return UUID.randomUUID();
    }

    @Override
    public UUID generate() {
        return generate(null);
    }
}
