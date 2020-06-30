package com.funbasetools.collections.internal;

import com.funbasetools.Lazy;
import com.funbasetools.collections.Stream;

public final class LazyTailStream<T> extends NonEmptyStream<T> {

    public static <T> Stream<T> create(T head, Lazy<Stream<T>> lazyTail) {
        return new LazyTailStream<>(head, lazyTail);
    }

    private final Lazy<Stream<T>> lazyTail;

    private LazyTailStream(T head, Lazy<Stream<T>> lazyTail) {
        super(head);
        this.lazyTail = lazyTail;
    }

    @Override
    public Stream<T> getTail() {
        return lazyTail.get();
    }

    public Lazy<Stream<T>> getLazyTail() {
        return lazyTail;
    }

    @Override
    public String toString() {
        return String.format("[ %s, ...]", getHead().toString());
    }
}
