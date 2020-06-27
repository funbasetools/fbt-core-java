package com.funbasetools;

import java.util.Objects;

@FunctionalInterface
public interface QuadFunction<A, B, C, D, R> {
    R apply(A a, B b, C c, D d);

    default <V> QuadFunction<A, B, C, D, V> andThen(final Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c, D d) -> after.apply(apply(a, b, c, d));
    }

    default TriFunction<B, C, D, R> curry(final A a) {
        return (b, c, d) -> apply(a, b, c, d);
    }
}
