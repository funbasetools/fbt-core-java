package com.funbasetools.collections.impl;

import com.funbasetools.Lazy;
import com.funbasetools.collections.Stream;
import java.util.Optional;
import java.util.function.Supplier;

public final class LazyStream<T> implements Stream<T> {

    private final Lazy<Stream<T>> lazyStream;

    public static <T> LazyStream<T> of(Supplier<Stream<T>> streamSupplier) {
        return of(Lazy.of(streamSupplier));
    }

    public static <T> LazyStream<T> of(Lazy<Stream<T>> lazyStream) {
        return new LazyStream<>(lazyStream);
    }

    private LazyStream(final Lazy<Stream<T>> lazyStream) {
        this.lazyStream = lazyStream;
    }

    @Override
    public Optional<T> getHeadOption() {
        return get().getHeadOption();
    }

    @Override
    public Stream<T> getTail() {
        return get().getTail();
    }

    @Override
    public boolean isEmpty() {
        return get().isEmpty();
    }

    @Override
    public boolean nonEmpty() {
        return get().nonEmpty();
    }

    public boolean isComputed() {
        return lazyStream.isComputed();
    }

    public Stream<T> get() {
        return lazyStream.get();
    }
}
