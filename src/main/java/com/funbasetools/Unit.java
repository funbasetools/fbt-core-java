package com.funbasetools;

import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(Unit.class)
            .build();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Unit;
    }

    @Override
    public String toString() {
        return "unit";
    }

    private Unit() {
    }
}
