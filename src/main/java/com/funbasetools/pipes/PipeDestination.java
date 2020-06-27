package com.funbasetools.pipes;

import com.funbasetools.Consumer;

public interface PipeDestination<M extends Message<?>> {

    void consume(final M msg);

    default Consumer<M> asConsumer() {
        return this::consume;
    }
}
