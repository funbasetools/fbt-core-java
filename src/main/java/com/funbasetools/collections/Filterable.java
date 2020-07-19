package com.funbasetools.collections;

import java.util.function.Predicate;

public interface Filterable<T> {

    boolean existIf(final Predicate<T> predicate, final boolean isTrue);

    Filterable<T> filterIf(final Predicate<T> predicate, final boolean isTrue);

    // implemented methods

    default boolean exist(final Predicate<T> predicate) {
        return existIf(predicate, true);
    }

    default boolean existNot(final Predicate<T> predicate) {
        return existIf(predicate, false);
    }

    default Filterable<T> filter(final Predicate<T> predicate) {
        return filterIf(predicate, true);
    }

    default Filterable<T> filterNot(final Predicate<T> predicate) {
        return filterIf(predicate, false);
    }
}
