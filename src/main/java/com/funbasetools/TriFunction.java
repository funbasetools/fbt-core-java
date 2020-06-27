package com.funbasetools;

import java.util.Objects;

@FunctionalInterface
public interface TriFunction<A, B, C, R> {

    R apply(A a, B b, C c);

    default <V> TriFunction<A, B, C, V> andThen(final Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c) -> after.apply(apply(a, b, c));
    }

    default BiFunction<B, C, R> curry(final A a) {
        return (b, c) -> apply(a, b, c);
    }
}
