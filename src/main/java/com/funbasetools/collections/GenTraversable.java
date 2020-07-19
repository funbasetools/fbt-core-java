package com.funbasetools.collections;

import java.util.function.Predicate;

public interface GenTraversable<T, TRV extends GenTraversable<T, TRV>>
    extends Traversable<T> {

    @Override
    TRV drop(final int count);

    @Override
    TRV dropWhileIf(final Predicate<T> predicate, final boolean isTrue);

    @Override
    TRV filterIf(final Predicate<T> predicate, final boolean isTrue);

    @Override
    default TRV dropWhile(final Predicate<T> predicate) {
        return dropWhileIf(predicate, true);
    }

    @Override
    default TRV dropWhileNot(final Predicate<T> predicate) {
        return dropWhileIf(predicate, false);
    }

    @Override
    default TRV filter(final Predicate<T> predicate) {
        return filterIf(predicate, true);
    }

    @Override
    default TRV filterNot(final Predicate<T> predicate) {
        return filterIf(predicate, false);
    }
}
