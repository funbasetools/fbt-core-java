package com.funbasetools.pipes;

import com.funbasetools.Consumer;
import com.funbasetools.Function;
import java.util.List;

public interface PipeSource<M extends Message<?>> {

    List<M> get(final int count);

    void acknowledge(final M msg);

    default Function<Integer, List<M>> asFunction() {
        return this::get;
    }

    default Consumer<M> asConsumer() {
        return this::acknowledge;
    }
}

