package com.funbasetools;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Generic class that can be used for lazy initialization.
 * This works accordingly to the definition of lazy evaluation in functional programming.
 * @since 0.1.1
 * @param <T> The generic type parameter of the lazy value
 */
public abstract class Lazy<T> {

    /**
     * This method returns a thread safe lazy object of the supplier function.
     * When get() is called in different threads, the value is computed only once.
     * This lazy object does use locks.
     *
     * @param supplier The supplier function to be evaluated on demand only once.
     * @param <A> The generic parameter of the lazy type
     * @return A thread safe lazy object
     */
    public static <A> Lazy<A> of(final Supplier<A> supplier) {
        return new SynchronizedLazy<>(supplier);
    }

    /**
     * This method returns a lazy object of the supplier function.
     * When get() is called in different threads, the value is computed only once.
     * This lazy object does use locks.
     *
     * @param supplier The supplier function to be evaluated on demand only once.
     * @param <A> The generic parameter of the lazy type
     * @return A thread safe lazy object
     */
    public static <A> Lazy<A> publicationOf(final Supplier<A> supplier) {
        return new PublishedLazy<>(supplier);
    }

    /**
     * This method returns a thread unsafe lazy object of the supplier function.
     * This lazy object doesn't use locks.
     *
     * @param supplier The supplier function to be evaluated on demand only once.
     * @param <A> The generic parameter of the lazy type
     * @return A thread unsafe lazy object
     */
    public static <A> Lazy<A> notSyncOf(final Supplier<A> supplier) {
        return new NotSynchronizedLazy<>(supplier);
    }

    protected T value;
    protected boolean computed;
    protected final Supplier<T> supplier;

    /**
     * This method checks if the value was already computed.
     *
     * @since 0.1.2
     * @return {@code true} if the value was already computed, otherwise returns {@code false}
     */
    public final boolean isComputed() {
        return computed;
    }

    /**
     * Computes the value only the first time and returns it.
     *
     * @return the computed value
     */
    public final T get() {
        computeIfNeeded();
        return value;
    }

    /**
     * Retrieves the value only if it's already computed.
     *
     * @return An optional with the value if it's already computed and it's not null, otherwise returns empty
     */
    public final Optional<T> getIfComputed() {
        return computed
            ? Optional.ofNullable(value)
            : Optional.empty();
    }

    /**
     * This method returns the string representation of the lazy object.
     * The string differs if the value is computed or not.
     *
     * @return The string representation of the lazy value
     */
    @Override
    public String toString() {
        return getIfComputed()
            .map(v -> String.format("Lazy(%s)", v))
            .orElse("Lazy(<Not Computed Yet>)");
    }

    protected Lazy(final Supplier<T> supplier) {
        this.value = null;
        this.computed = false;
        this.supplier = supplier;
    }

    protected void computeIfNeeded() {
        if (!computed) {
            value = supplier.get();
            computed = true;
        }
    }
}

final class NotSynchronizedLazy<T> extends Lazy<T> {

    NotSynchronizedLazy(Supplier<T> supplier) {
        super(supplier);
    }
}

final class SynchronizedLazy<T> extends Lazy<T> {

    private final Object syncObj = new Object();

    SynchronizedLazy(Supplier<T> supplier) {
        super(supplier);
    }

    @Override
    protected void computeIfNeeded() {
        if (!isComputed()) {
            synchronized (syncObj) {
                super.computeIfNeeded();
            }
        }
    }
}

final class PublishedLazy<T> extends Lazy<T> {

    private final Object syncObj = new Object();

    PublishedLazy(Supplier<T> supplier) {
        super(supplier);
    }

    @Override
    protected void computeIfNeeded() {
        if (!computed) {
            final T computedValue = supplier.get();
            synchronized (syncObj) {
                if (!computed) {
                    value = computedValue;
                    computed = true;
                }
            }
        }
    }
}
