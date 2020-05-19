package com.funbasetools.certainties;

import java.util.function.Function;

public final class Known<T> extends NotUnknown<T> {

    Known(T value) {
        super(value);
    }

    @Override
    public int compareCertainty(Knowable<T> other) {
        return other.isKnown() ? 0 : 1;
    }

    @Override
    public boolean isKnown() {
        return true;
    }

    @Override
    public boolean isPartiallyKnown() {
        return false;
    }

    @Override
    public Knowable<T> transform(Function<T, T> function) {
        return Knowable.known(function.apply(get()));
    }

    @Override
    public String toString() {
        return String.format("Known(%s)", get());
    }
}
