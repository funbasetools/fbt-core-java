package com.funbasetools.id;

import com.funbasetools.UtcDateUtils;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public interface UUIDGenerator extends IdGenerator<UUID, Instant>, Serializable {

    int getGeneratedVersion();

    UUID generate(final Instant instant);

    default UUID generate() {
        return generate(UtcDateUtils.instantNow());
    }
}
