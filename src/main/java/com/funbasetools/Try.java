package com.funbasetools;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Try<T> {

    public static Try<Unit> of(final ThrowingRunnable<? extends Exception> runnable) {
        return of(Unit.from(runnable));
    }

    public static <T> Try<T> of(final ThrowingSupplier<T, ? extends  Exception> supplier) {
        try {
            final T result = supplier.get();
            return success(result);
        }
        catch (final Exception ex) {
            return failure(ex);
        }
    }

    public static <T> Try<T> success(final T result) {
        return new Success<>(result);
    }

    public static <T, E extends Exception> Try<T> failure(final E ex) {
        return new Failure<>(ex);
    }

    public abstract Optional<T> toOptional();

    public abstract Optional<Exception> toFailureOptional();

    public abstract boolean isSuccess();

    public boolean isFailure() {
        return !isSuccess();
    }

    public Try<T> ifSuccess(final Consumer<T> consumer) {
        toOptional().ifPresent(consumer);
        return this;
    }

    public Try<T> ifFailure(final Consumer<Exception> consumer) {
        toFailureOptional().ifPresent(consumer);
        return this;
    }

    public <R> Try<R> flatMap(final Function<T, Try<R>> f) {
        return toOptional()
            .map(f)
            .orElseGet(() ->
                failure(
                    toFailureOptional().orElse(new Exception("Unknown error"))
                )
            );
    }

    public <R> Try<R> map(final ThrowingFunction<T, R, ? extends Exception> f) {
        return toOptional()
            .map(result -> Try.of(() -> f.apply(result)))
            .orElseGet(() ->
                failure(
                    toFailureOptional().orElse(new Exception("Unknown error"))
                )
            );
    }

    private static final class Success<T> extends Try<T> {

        private final T result;

        private Success(T result) {
            this.result = result;
        }

        @Override
        public Optional<T> toOptional() {
            return Optional.ofNullable(result);
        }

        @Override
        public Optional<Exception> toFailureOptional() {
            return Optional.empty();
        }

        @Override
        public boolean isSuccess() {
            return true;
        }
    }

    private static final class Failure<T> extends Try<T> {

        private final Exception exception;

        private Failure(Exception exception) {
            this.exception = exception;
        }

        @Override
        public Optional<T> toOptional() {
            return Optional.empty();
        }

        @Override
        public Optional<Exception> toFailureOptional() {
            return Optional.of(exception);
        }

        @Override
        public boolean isSuccess() {
            return false;
        }
    }
}
