package com.funbasetools.id;

import com.funbasetools.UtcDateUtils;
import java.io.Serializable;
import java.util.UUID;

public interface UUIDGenerator extends IdGenerator<UUID, Long>, Serializable {

    int getGeneratedVersion();

    default UUID generate() {
        return generate(UtcDateUtils.timestampNow());
    }
}
