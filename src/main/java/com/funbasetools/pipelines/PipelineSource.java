package com.funbasetools.pipelines;

import com.funbasetools.collections.Stream;

public interface PipelineSource<T, M extends PipelineMessage<T>> {

    Stream<M> get(int count);

    void release(M message);
}
