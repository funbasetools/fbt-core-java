package com.funbasetools;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {

    R apply(T arg) throws E;
}
