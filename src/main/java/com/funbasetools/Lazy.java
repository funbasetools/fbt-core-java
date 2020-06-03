package com.funbasetools;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Generic class that can be used for lazy initialization.
 * This works accordingly to the definition of lazy evaluation in functional programming.
 * @since 0.1.1
 * @param <T> The generic type parameter of the lazy value
 */
public final class Lazy<T> {

    /**
     * This method returns a lazy object of the supplier function.
     * @param supplier The supplier function to be evaluated on demand only once.
     * @param <A> The generic parameter of the lazy type
     * @return The lazy object
     */
    public static <A> Lazy<A> of(Supplier<A> supplier) {
        return new Lazy<>(supplier);
    }

    private T value;
    private boolean computed;
    private final Supplier<T> supplier;
    private final Object syncObj = new Object();

    private Lazy(Supplier<T> supplier) {
        this.value = null;
        this.computed = false;
        this.supplier = supplier;
    }

    /**
     * This method checks if the value was already computed.
     * This method is Thread safe.
     * @since 0.1.2
     * @return {@code true} if the value was already computed, otherwise returns {@code false}
     */
    public boolean isComputed() {
        return computed;
    }

    /**
     * Computes the value only the first time and returns it.
     * This method is Thread safe.
     * @return the computed value
     */
    public T get() {
        if (!computed) {
            synchronized (syncObj) {
                if (!computed) {
                    value = supplier.get();
                    computed = true;
                }
            }
        }

        return value;
    }

    /**
     * Retrieves the value only if it's already computed.
     * This method is Thread safe
     * @return An optional with the value if it's already computed and it's not null, otherwise returns empty
     */
    public Optional<T> getIfComputed() {
        return computed
            ? Optional.ofNullable(value)
            : Optional.empty();
    }

    /**
     * This method returns the string representation of the lazy object.
     * The string differs if the value is computed or not.
     * @return The string representation of the lazy value
     */
    @Override
    public String toString() {
        return getIfComputed()
            .map(v -> String.format("Lazy(%s)", v))
            .orElse("Lazy(<Not Computed Yet>)");
    }
}
