package com.funbasetools.certainties;

import com.funbasetools.Numbers;

import java.util.function.Function;
import java.util.function.Supplier;

public interface Knowable<T> {

    T get();

    int compareCertainty(Knowable<T> other);

    boolean isUnknown();

    boolean isKnown();

    default boolean isEquallyCertainTo(Knowable<T> other) {
        return compareCertainty(other) == 0;
    }

    default boolean isLessCertainThan(Knowable<T> other) {
        return compareCertainty(other) < 0;
    }

    default boolean isMoreCertainThan(Knowable<T> other) {
        return compareCertainty(other) > 0;
    }

    boolean isPartiallyKnown();

    default T orElse(T value) {
        if (isUnknown()) {
            return value;
        }

        return get();
    }

    default Knowable<T> orElse(Knowable<T> other) {
        if (isUnknown()) {
            return other;
        }

        return this;
    }

    default Knowable<T> orElseGet(final Supplier<Knowable<T>> supplier) {
        if (isUnknown()) {
            return supplier.get();
        }

        return this;
    }

    Knowable<T> transform(Function<T, T> function);

    static <T> Unknown<T> unknown() {
        return Unknown.getInstance();
    }

    static <T> Known<T> known(T value) {
        return new Known<>(value);
    }

    static <N extends Number> AtLeast<N> atLeast(N count) {
        return new AtLeast<>(count);
    }

    static <N extends Number> AtMost<N> atMost(N count) {
        return new AtMost<>(count);
    }

    static <N extends Number> boolean isKnownThatIsLessOrEqualsTo(final Knowable<N> knowable, Number value) {

        return (knowable.isKnown() || knowable instanceof AtMost)
            && Numbers.compare(knowable.get(), value) <= 0;
    }

    @SafeVarargs
    static <T> Knowable<T> getMostCertain(Knowable<T>... knowableArray) {
        Knowable<T> mostCertain = Knowable.unknown();
        for (Knowable<T> knowable: knowableArray) {
            if (knowable.isMoreCertainThan(mostCertain)) {
                mostCertain = knowable;
            }
        }

        return mostCertain;
    }
}
