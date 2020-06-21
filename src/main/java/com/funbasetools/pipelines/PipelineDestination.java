package com.funbasetools.pipelines;

import com.funbasetools.Try;
import com.funbasetools.Unit;

@FunctionalInterface
public interface PipelineDestination<T, M extends PipelineMessage<T>> {

    Try<Unit> send(final M message);
}
