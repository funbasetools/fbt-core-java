package com.funbasetools.pipelines.messages;

import java.time.Instant;

public interface TemporalMessage extends Message {

    Instant getInstant();
}
