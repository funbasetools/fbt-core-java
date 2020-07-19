package com.funbasetools.collections;

import java.util.function.Predicate;

public interface GenFilterable<T, F extends GenFilterable<T, F>> extends Filterable<T> {

    @Override
    F filterIf(final Predicate<T> predicate, final boolean isTrue);

    // implemented methods

    @Override
    default F filter(final Predicate<T> predicate) {
        return filterIf(predicate, true);
    }

    @Override
    default F filterNot(final Predicate<T> predicate) {
        return filterIf(predicate, false);
    }
}
