package com.funbasetools.collections.internal;

import com.funbasetools.collections.Stream;

public final class ConsStream<T> extends NonEmptyStream<T> {

    public static <T> Stream<T> create(T head, Stream<T> tail) {
        return new ConsStream<>(head, tail);
    }

    private final Stream<T> tail;

    private ConsStream(T head, Stream<T> tail) {
        super(head);
        this.tail = tail;
    }

    @Override
    public Stream<T> getTail() {
        return tail;
    }
}
