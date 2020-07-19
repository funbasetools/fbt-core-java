package com.funbasetools.collections;

import static com.funbasetools.collections.Streams.of;

import com.funbasetools.ThrowingConsumer;
import com.funbasetools.TriFunction;
import com.funbasetools.Try;
import com.funbasetools.Types;
import com.funbasetools.Unit;
import com.funbasetools.collections.impl.EmptyStream;
import com.funbasetools.collections.impl.FilteredStream;
import com.funbasetools.collections.impl.MappedStream;
import com.funbasetools.collections.impl.ZippedStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.apache.commons.lang3.tuple.Pair;

public interface Stream<T>
    extends GenTraversable<T, Stream<T>>,
            HeadGrowable<T, Stream<T>>,
            TailGrowable<T, Stream<T>> {

    static <T> Stream<T> empty() {
        return EmptyStream.getInstance();
    }

    static <T, IT extends Iterable<T>> Stream<T> flatten(final Stream<IT> stream) {
        return stream.flatMap(s -> s);
    }

    Optional<T> getHeadOption();

    Stream<T> getTail();

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

    default <R extends T> Stream<R> castTo(final Class<R> type) {
        return map(it -> Types.as(type, it));
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

    default Stream<T> dropWhileIf(final Predicate<T> predicate, final boolean isTrue) {
        return foldLeftWhile(
            this,
            (r, it) -> predicate.test(it) == isTrue,
            (r, it) -> r.getTail()
        );
    }

    default Stream<T> filterIf(final Predicate<T> predicate, final boolean isTrue) {
        return FilteredStream.of(this, predicate, isTrue);
    }

    default <R> Stream<R> flatMapOptional(final Function<? super T, Optional<R>> f) {
        return map(f)
            .filter(Optional::isPresent)
            .map(Optional::get);
    }

    default <R> Stream<R> flatMap(final Function<? super T, ? extends Iterable<R>> f) {

        final Stream<Stream<R>> mappedStream = map(it -> of(f.apply(it)));

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
                .orElse(empty()))
            .orElse(empty());
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

    default <R> R foldLeftWithIndex(final R initialValue, final TriFunction<R, T, Integer, R> function) {
        R result = initialValue;
        int idx = 0;
        Stream<T> curr = this;
        while (curr.getHeadOption().isPresent()) {
            result = function.apply(result, curr.getHeadOption().get(), idx);
            curr = curr.getTail();
            idx += 1;
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
        while (curr.nonEmpty() && predicate.apply(result, curr.getHeadOption().orElse(null))
        ) {
            result = function.apply(result, curr.getHeadOption().orElse(null));
            curr = curr.getTail();
        }

        return result;
    }

    default <R> Stream<R> map(final Function<? super T, R> function) {
        return MappedStream.of(this, function);
    }

    default Optional<T> nthIf(
        final int nth,
        final Predicate<T> predicate,
        final boolean isTrue
    ) {
        return this
            .filterIf(predicate, isTrue)
            .drop(nth)
            .getHeadOption();
    }

    default Stream<T> prepend(final T item) {
        return Streams.of(item, this);
    }

    default Stream<T> prepend(final Stream<T> stream) {
        return stream.append(this);
    }

    default Pair<List<T>, Stream<T>> takeAndDrop(final int count) {
        return foldLeftWhile(
            Pair.of(new ArrayList<>(count), this),
            (pair, it) -> pair.getLeft().size() < count,
            (pair, it) -> {
                pair.getLeft().add(it);
                return Pair.of(pair.getLeft(), pair.getRight().getTail());
            }
        );
    }

    default Pair<List<T>, Stream<T>> takeAndDropWhile(final Predicate<T> predicate) {
        return takeAndDropWhileIf(predicate, true);
    }

    default Pair<List<T>, Stream<T>> takeAndDropWhileNot(final Predicate<T> predicate) {
        return takeAndDropWhileIf(predicate, false);
    }

    default Pair<List<T>, Stream<T>> takeAndDropWhileIf(final Predicate<T> predicate, final boolean isTrue) {
        return foldLeftWhile(
            Pair.of(new LinkedList<>(), this),
            (pair, it) -> predicate.test(it) == isTrue,
            (pair, it) -> {
                pair.getLeft().add(it);
                return Pair.of(pair.getLeft(), pair.getRight().getTail());
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
        return ZippedStream.of(this, other);
    }

    default Stream<Pair<T, Integer>> zipWithIndex() {
        return ZippedStream.of(this, Streams.from(0));
    }

    @Override
    default Iterator<T> iterator() {
        final Stream<T> $this = this;

        return new Iterator<>() {

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
    default void forEach(final Consumer<? super T> action) {
        for (T item: this) {
            action.accept(item);
        }
    }

    default <E extends Exception> void throwingForEach(final ThrowingConsumer<? super T, E> action) throws E {
        for (T item: this) {
            action.accept(item);
        }
    }

    default <E extends Exception> Try<Unit> tryForEach(final ThrowingConsumer<? super T, E> action) {
        return Try.of(() -> throwingForEach(action));
    }

    default void forEachWithIndex(final BiConsumer<? super T, Integer> action) {
        int idx = 0;
        for (T item: this) {
            action.accept(item, idx);
            idx += 1;
        }
    }

    @Override
    default Spliterator<T> spliterator() {
        final Stream<T> owner = this;

        return new Spliterator<>() {

            private Stream<T> stream = owner;

            @Override
            public boolean tryAdvance(Consumer<? super T> action) {
                return stream
                    .getHeadOption()
                    .map(head -> {
                        action.accept(head);
                        stream = stream.getTail();
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
                return -1;
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
            Stream<T> stream = empty();
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
