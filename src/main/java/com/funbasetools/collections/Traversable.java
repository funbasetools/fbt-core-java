package com.funbasetools.collections;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Traversable<T> extends GenFilterable<T, Traversable<T>>, Iterable<T> {

    <R extends T> Traversable<R> castTo(final Class<R> type);

    Traversable<T> drop(final int count);

    Traversable<T> dropWhileIf(final Predicate<T> predicate, final boolean isTrue);

    default Traversable<T> dropWhile(final Predicate<T> predicate) {
        return dropWhileIf(predicate, true);
    }

    default Traversable<T> dropWhileNot(final Predicate<T> predicate) {
        return dropWhileIf(predicate, false);
    }

    <R> R foldLeftWhile(final R initialValue,
                        final BiFunction<R, T, Boolean> predicate,
                        final BiFunction<R, T, R> function);

    boolean isEmpty();

    boolean nonEmpty();

    Optional<T> nthIf(final int nth, final Predicate<T> predicate, final boolean isTrue);

    // implemented methods

    default boolean existIf(final Predicate<T> predicate, final boolean isTrue) {
        return foldLeftWhile(
            !isTrue,
            (r, it) -> r != isTrue,
            (r, it) -> predicate.test(it) == isTrue
        );
    }

    default Optional<T> first(final Predicate<T> predicate) {
        return nth(0, predicate);
    }

    default Optional<T> firstNot(final Predicate<T> predicate) {
        return nthNot(0, predicate);
    }

    default boolean forAll(final Predicate<T> predicate) {
        return forAllIf(predicate, true);
    }

    default boolean forAllNot(final Predicate<T> predicate) {
        return forAllIf(predicate, false);
    }

    default boolean forAllIf(final Predicate<T> predicate, final boolean isTrue) {
        return foldLeftWhile(
            isTrue,
            (r, it) -> r == isTrue,
            (r, it) -> r && predicate.test(it)
        );
    }

    default int forEachWhile(final Predicate<T> predicate, final Consumer<T> consumer) {
        return forEachWhileIf(predicate, consumer, true);
    }

    default int forEachWhileNot(final Predicate<T> predicate, final Consumer<T> consumer) {
        return forEachWhileIf(predicate, consumer, false);
    }

    default int forEachWhileIf(final Predicate<T> predicate,
                               final Consumer<T> consumer,
                               final boolean isTrue) {
        return foldLeftWhile(
            0,
            (r, it) -> predicate.test(it) == isTrue,
            (r, it) -> {
                consumer.accept(it);
                return r + 1;
            }
        );
    }

    default Optional<T> nth(final int nth, final Predicate<T> predicate) {
        return nthIf(nth, predicate, true);
    }

    default Optional<T> nthNot(final int nth, final Predicate<T> predicate) {
        return nthIf(nth, predicate, false);
    }

    default List<T> take(final int count) {
        return foldLeftWhile(
            new ArrayList<>(count),
            (r, it) -> r.size() < count,
            (r, it) ->  {
                r.add(it);
                return r;
            }
        );
    }

    default List<T> takeWhile(final Predicate<T> predicate) {
        return takeWhileIf(predicate, true);
    }

    default List<T> takeWhileNot(final Predicate<T> predicate) {
        return takeWhileIf(predicate, false);
    }

    default List<T> takeWhileIf(final Predicate<T> predicate, final boolean isTrue) {
        return foldLeftWhile(
            new LinkedList<>(),
            (r, it) -> predicate.test(it) == isTrue,
            (r, it) -> {
                r.addLast(it);
                return r;
            }
        );
    }
}
