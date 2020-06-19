package com.funbasetools.pipelines;

import com.funbasetools.Try;
import com.funbasetools.Unit;
import com.funbasetools.pipelines.messages.Message;

@FunctionalInterface
public interface PipelineDestination {

    Try<Unit> send(final Message message);
}
