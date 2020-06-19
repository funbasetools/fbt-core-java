package com.funbasetools;

import java.util.function.Supplier;

public final class Unit {

    public static final Unit value = new Unit();

    public static Supplier<Unit> from(final Runnable runnable) {
        return () -> {
            runnable.run();
            return value;
        };
    }

    public static <E extends Exception> ThrowingSupplier<Unit, E> from(final ThrowingRunnable<E> runnable) {
        return () -> {
            runnable.run();
            return value;
        };
    }

    private Unit() {
    }
}
