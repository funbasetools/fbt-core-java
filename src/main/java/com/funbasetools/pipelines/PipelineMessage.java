package com.funbasetools.pipelines;

public interface PipelineMessage<T> {

    String getId();
    T getContent();
}
