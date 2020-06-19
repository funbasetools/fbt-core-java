package com.funbasetools.pipelines;

import com.funbasetools.collections.Stream;
import com.funbasetools.pipelines.messages.Message;

public interface PipelineSource<M extends Message> {

    Stream<M> get(int count);

    void release(M message);
}
