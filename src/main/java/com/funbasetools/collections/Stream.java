package com.funbasetools.collections;

import com.funbasetools.certainties.Knowable;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.*;

import static com.funbasetools.FBT.asStream;
import static com.funbasetools.FBT.unknown;

public interface Stream<T> extends Iterable<T> {

    Optional<T> getHeadOption();

    Stream<T> getTail();

    default Knowable<Long> size() {
        return unknown();
    }

    default boolean isEmpty() {
        return !nonEmpty();
    }

    default boolean nonEmpty() {
        return getHeadOption().isPresent();
    }

    default Stream<T> append(final T item) {
        return getHeadOption()
            .map(head -> Streams.of(head, () -> getTail().append(item)))
            .orElse(Streams.singleton(item));
    }

    default Stream<T> append(final Stream<T> stream) {
        return getHeadOption()
            .map(head -> Streams.of(head, () -> getTail().append(stream)))
            .orElse(stream);
    }

    default Stream<T> append(final Supplier<Stream<T>> getStreamFunc) {
        return getHeadOption()
            .map(head -> Streams.of(head, () -> getTail().append(getStreamFunc)))
            .orElseGet(getStreamFunc);
    }

    default <B> boolean corresponds(final Stream<B> other) {
        return corresponds(other, Objects::equals);
    }

    default <B> boolean corresponds(final Stream<B> other, BiFunction<T, B, Boolean> p) {
        return zip(other)
            .forAll(t -> p.apply(t.getLeft(), t.getRight()));
    }

    default Stream<T> drop(final int count) {
        Stream<T> result = this;
        for (int i = 0; i < count && nonEmpty(); i++) {
            result = result.getTail();
        }

        return result;
    }

    default Stream<T> dropWhile(final Predicate<T> predicate) {
        return dropWhileIf(predicate, true);
    }

    default Stream<T> dropWhileNot(final Predicate<T> predicate) {
        return dropWhileIf(predicate, false);
    }

    default Stream<T> dropWhileIf(final Predicate<T> predicate, final boolean isTrue) {
        return foldLeftWhile(
            this,
            (r, it) -> predicate.test(it) == isTrue,
            (r, it) -> r.getTail()
        );
    }

    default boolean exist(final Predicate<T> predicate) {
        return existIf(predicate, true);
    }

    default boolean existNot(final Predicate<T> predicate) {
        return existIf(predicate, false);
    }

    default boolean existIf(final Predicate<T> predicate, boolean isTrue) {
        return foldLeftWhile(
            false,
            (r, it) -> r != isTrue,
            (r, it) -> predicate.test(it) == isTrue
        );
    }

    default Stream<T> filter(final Predicate<T> predicate) {
        return Streams.withFilter(this, predicate, true);
    }

    default Stream<T> filterNot(final Predicate<T> predicate) {
        return Streams.withFilter(this, predicate, false);
    }

    default Optional<T> first(final Predicate<T> predicate) {
        return nth(0, predicate);
    }

    default Optional<T> firstNot(final Predicate<T> predicate) {
        return nthNot(0, predicate);
    }

    default <R> Stream<R> flatMap(final Function<T, ? extends Iterable<R>> f) {

        final Stream<Stream<R>> mappedStream = map(it -> asStream(f.apply(it)));

        final Stream<Pair<T, Stream<R>>> pairedStream = zip(mappedStream)
            .dropWhile(pair -> pair.getRight().isEmpty());

        return pairedStream
            .getHeadOption()
            .map(pair -> pair
                .getRight()
                .getHeadOption()
                .map(head -> {
                    final Supplier<Stream<R>> getTailFunc = () -> pairedStream
                        .getTail()
                        .unzip(p -> p)
                        .getLeft()
                        .flatMap(f);

                    final Stream<R> tail = pair
                        .getRight()
                        .getTail()
                        .append(getTailFunc);

                    return Streams.of(head, tail);
                })
                .orElse(Streams.emptyStream()))
            .orElse(Streams.emptyStream());
    }

    default <R> R foldLeft(final R initialValue, final BiFunction<R, T, R> function) {
        R result = initialValue;
        Stream<T> curr = this;
        while (curr.getHeadOption().isPresent()) {
            result = function.apply(result, curr.getHeadOption().get());
            curr = curr.getTail();
        }

        return result;
    }

    default <R> R foldLeftFirsts(int count, final R initialValue, final BiFunction<R, T, R> function) {
        R result = initialValue;
        Stream<T> curr = this;
        for (int i = 0; i < count && curr.getHeadOption().isPresent(); i++) {
            result = function.apply(result, curr.getHeadOption().get());
            curr = curr.getTail();
        }

        return result;
    }

    default <R> R foldLeftWhile(
        final R initialValue,
        final BiFunction<R, T, Boolean> predicate,
        final BiFunction<R, T, R> function
    ) {
        R result = initialValue;
        Stream<T> curr = this;
        while (curr.getHeadOption().isPresent()
            && predicate.apply(result, curr.getHeadOption().get())
        ) {
            result = function.apply(result, curr.getHeadOption().get());
            curr = curr.getTail();
        }

        return result;
    }

    default int forEachWhile(final Predicate<T> predicate, final Consumer<T> consumer) {
        return forEachWhileIf(predicate, consumer, true);
    }

    default int forEachWhileNot(final Predicate<T> predicate, final Consumer<T> consumer) {
        return forEachWhileIf(predicate, consumer, false);
    }

    default int forEachWhileIf(
        final Predicate<T> predicate, final Consumer<T> consumer,
        final boolean isTrue
    ) {
        return foldLeftWhile(
            0,
            (r, it) -> predicate.test(it) == isTrue,
            (r, it) -> {
                consumer.accept(it);
                return r + 1;
            }
        );
    }

    default boolean forAll(final Predicate<T> predicate) {
        return foldLeftWhile(
            true,
            (r, it) -> r,
            (r, it) -> r && predicate.test(it)
        );
    }

    default <R> Stream<R> map(final Function<T, R> function) {
        return Streams.withMapFunction(this, function);
    }

    default Optional<T> nth(final int nth, final Predicate<T> predicate) {
        return nthIf(nth, predicate, true);
    }

    default Optional<T> nthNot(final int nth, final Predicate<T> predicate) {
        return nthIf(nth, predicate, false);
    }

    default Optional<T> nthIf(
        final int nth,
        final Predicate<T> predicate,
        final boolean isTrue
    ) {
        return Streams
            .withFilter(this, predicate, isTrue)
            .drop(nth)
            .getHeadOption();
    }

    default Stream<T> prepend(final T item) {
        return Streams.of(item, this);
    }

    default Stream<T> prepend(final Stream<T> stream) {
        return stream.append(this);
    }

    default List<T> take(final int count) {
        return foldLeftWhile(
            new ArrayList<>(StrictMath.min(100, count)),
            (r, it) -> r.size() < count,
            (r, it) -> {
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

    default <A, B> Pair<Stream<A>, Stream<B>> unzip(final Function<T, Pair<A, B>> f) {
        final Stream<Pair<A, B>> pairStream = map(f);

        return Pair.of(
            pairStream.map(Pair::getLeft),
            pairStream.map(Pair::getRight)
        );
    }

    default <W> Stream<Pair<T, W>> zip(Stream<W> other) {
        return Streams.zipStreams(this, other);
    }

    default Stream<Pair<T, Integer>> zipWithIndex() {
        return Streams.zipStreams(this, Streams.from(0));
    }

    @Override
    default Iterator<T> iterator() {
        final Stream<T> $this = this;

        return new Iterator<T>() {

            private Stream<T> owner = $this;

            @Override
            public boolean hasNext() {
                return owner.nonEmpty();
            }

            @Override
            public T next() {
                final T head = owner.getHeadOption().orElse(null);
                owner = owner.getTail();

                return head;
            }
        };
    }

    @Override
    default void forEach(Consumer<? super T> action) {
        for (T item: this) {
            action.accept(item);
        }
    }

    @Override
    default Spliterator<T> spliterator() {
        final Stream<T> owner = this;

        return new Spliterator<T>() {

            private Knowable<Long> estimateSize = size();
            private Stream<T> stream = owner;

            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                return stream
                    .getHeadOption()
                    .map(head -> {
                        action.accept(head);

                        stream = stream.getTail();
                        estimateSize = stream.size();

                        return true;
                    })
                    .orElse(false);
            }

            @Override
            public Spliterator<T> trySplit() {
                return null;
            }

            @Override
            public long estimateSize() {
                return estimateSize.orElse(-1L);
            }

            @Override
            public int characteristics() {
                return Spliterator.NONNULL
                    | Spliterator.SIZED
                    | Spliterator.SUBSIZED
                    | Spliterator.ORDERED
                    | Spliterator.IMMUTABLE;
            }
        };
    }

    // Builder

    final class Builder<T> {

        private final Stack<T> stackedItems = new Stack<>();

        public Stream<T> build() {
            Stream<T> stream = Streams.emptyStream();
            while (!stackedItems.empty()) {
                stream = stream.prepend(stackedItems.pop());
            }

            return stream;
        }

        public Builder<T> append(T item) {
            stackedItems.push(item);
            return this;
        }
    }
}
