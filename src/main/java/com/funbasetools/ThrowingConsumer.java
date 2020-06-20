package com.funbasetools;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Exception> {

    void accept(final T arg) throws E;
}
