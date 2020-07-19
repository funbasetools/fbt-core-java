package com.funbasetools.collections.impl;

import com.funbasetools.collections.Stream;

public class LazyTailStream<T> extends NonEmptyStream<T> {

    public static <T> Stream<T> of(T head, LazyStream<T> lazyTail) {
        return new LazyTailStream<>(head, lazyTail);
    }

    private final LazyStream<T> lazyTail;

    protected LazyTailStream(final T head, final LazyStream<T> lazyTail) {
        super(head);
        this.lazyTail = lazyTail;
    }

    @Override
    public Stream<T> getTail() {
        return lazyTail;
    }

    @Override
    public String toString() {
        return String.format("[ %s, ...]", getHead().toString());
    }
}
