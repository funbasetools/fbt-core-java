package com.funbasetools;

import java.util.Objects;

@FunctionalInterface
public interface Function<T, R> extends java.util.function.Function<T, R> {

    R apply(T t);

    default Consumer<T> andThen(final Consumer<R> consumer) {
        Objects.requireNonNull(consumer);
        return arg -> consumer.accept(apply(arg));
    }

    default <V> Function<T, V> andThen(final Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return arg -> after.apply(apply(arg));
    }

    default Supplier<R> compose(final Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier);
        return () -> apply(supplier.get());
    }

    default <V> Function<V, R> compose(final Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return arg -> apply(before.apply(arg));
    }

    default Supplier<R> curry(final T arg) {
        return () -> apply(arg);
    }

    static <T, R> Function<T, R> of(java.util.function.Function<T, R> function) {
        return function::apply;
    }

    static <T> Function<T, T> identity() {
        return arg -> arg;
    }
}
