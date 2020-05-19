package com.funbasetools;

import java.util.Optional;
import java.util.function.Supplier;

public final class Lazy<T> {

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
     * Computes the value only the first time and returns it
     *
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
     * Retrieves the value only if it's already computed
     *
     * @return An optional with the value if it's already computed and it's not null, otherwise returns empty
     */
    public Optional<T> getIfComputed() {
        return computed
            ? Optional.ofNullable(value)
            : Optional.empty();
    }

    @Override
    public String toString() {
        return getIfComputed()
            .map(v -> String.format("Lazy(%s)", v))
            .orElse("Lazy(<Not Computed Yet>)");
    }
}
