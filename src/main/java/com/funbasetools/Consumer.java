package com.funbasetools;

import java.util.Objects;

@FunctionalInterface
public interface Consumer<T> extends java.util.function.Consumer<T> {

    void accept(final T arg);

    default Runnable compose(final java.util.function.Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier);
        return () -> accept(supplier.get());
    }

    default <A> Consumer<A> compose(final java.util.function.Function<? super A, ? extends T> f) {
        Objects.requireNonNull(f);
        return arg -> accept(f.apply(arg));
    }

    default Runnable curry(final T arg) {
        return () -> accept(arg);
    }

    static <T> Consumer<T> of(java.util.function.Consumer<T> consumer) {
        return consumer::accept;
    }
}
