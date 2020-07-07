package com.funbasetools;

import java.util.Objects;

@FunctionalInterface
public interface Supplier<T> extends java.util.function.Supplier<T> {

    T get();

    default Runnable andFinally(final java.util.function.Consumer<? super T> consumer) {
        Objects.requireNonNull(consumer);
        return () -> consumer.accept(get());
    }

    default <R> Supplier<R> andThen(final java.util.function.Function<? super T, ? extends R> f) {
        Objects.requireNonNull(f);
        return () -> f.apply(get());
    }

    static <T> Supplier<T> of(java.util.function.Supplier<T> supplier) {
        return supplier::get;
    }
}
