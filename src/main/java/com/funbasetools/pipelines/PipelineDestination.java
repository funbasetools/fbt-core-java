package com.funbasetools.pipelines;

import com.funbasetools.Try;
import com.funbasetools.pipelines.messages.Message;

@FunctionalInterface
public interface PipelineDestination {

    Try<?> send(final Message message);
}
