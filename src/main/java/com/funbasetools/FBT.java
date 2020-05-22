package com.funbasetools;

import com.funbasetools.certainties.Knowable;
import com.funbasetools.collections.Stream;
import com.funbasetools.collections.Streams;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;

public final class FBT {

    // Basic

    public static <T> Lazy<T> lazy(final Supplier<T> supplier) {
        return Lazy.of(supplier);
    }

    public static <A, B> Pair<A, B> pair(A a, B b) {
        return Pair.of(a, b);
    }

    // Certainty

    public static <T> Knowable<T> unknown() {
        return Knowable.unknown();
    }

    public static <T> Knowable<T> known(final T value) {
        return Knowable.known(value);
    }

    public static <T extends Number> Knowable<T> atLeast(final T value) {
        return Knowable.atLeast(value);
    }

    public static <T extends Number> Knowable<T> atMost(final T value) {
        return Knowable.atMost(value);
    }

    // Collections

    @SafeVarargs
    public static <T> Stream<T> asStream(final T...array) {
        return Streams.of(array);
    }

    public static <T> Stream<T> asStream(final Iterable<T> iterable) {
        return Streams.of(iterable);
    }

    public static Stream<Character> asStream(final String string) {
        return Streams.of(string);
    }
}
