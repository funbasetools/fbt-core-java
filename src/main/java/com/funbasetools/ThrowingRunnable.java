package com.funbasetools;

@FunctionalInterface
public interface ThrowingRunnable<E extends Exception> {

    void run() throws E;
}
