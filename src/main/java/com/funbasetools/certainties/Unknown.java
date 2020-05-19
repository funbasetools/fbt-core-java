package com.funbasetools.certainties;

import java.util.function.Function;

public final class Unknown<T> implements Knowable<T> {

    private static final Unknown<?> singleton = new Unknown<>();

    public static <T> Unknown<T> getInstance() {
        @SuppressWarnings("unchecked")
        final Unknown<T> unknown = (Unknown<T>)singleton;

        return unknown;
    }

    private Unknown() { }

    @Override
    public T get() {
        throw new UnsupportedOperationException("Cannot get value from an Unknown");
    }

    @Override
    public int compareCertainty(Knowable<T> other) {
        return other.isUnknown() ? 0 : -1;
    }

    @Override
    public boolean isUnknown() {
        return true;
    }

    @Override
    public boolean isKnown() {
        return false;
    }

    @Override
    public boolean isPartiallyKnown() {
        return false;
    }

    @Override
    public Knowable<T> transform(Function<T, T> function) {
        return getInstance();
    }

    @Override
    public String toString() {
        return "Unknown";
    }
}
