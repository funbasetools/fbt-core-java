package com.funbasetools.pipelines;

import com.funbasetools.Try;
import com.funbasetools.Unit;
import com.funbasetools.pipelines.messages.Message;

@FunctionalInterface
public interface PipelineDestination<M extends Message> {

    Try<Unit> send(final M message);
}
