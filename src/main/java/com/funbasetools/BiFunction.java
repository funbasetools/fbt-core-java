package com.funbasetools;

import java.util.Objects;

@FunctionalInterface
public interface BiFunction<A, B, R> {

    R apply(A a, B b);

    default <V> BiFunction<A, B, V> andThen(final Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b) -> after.apply(apply(a, b));
    }

    default Function<B, R> curry(final A a) {
        return b -> apply(a, b);
    }
}
