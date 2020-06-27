package com.funbasetools.collections;

import com.funbasetools.Lazy;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.*;
import java.util.function.*;

public final class Streams {

    private Streams() { }

    public static <T> Stream<T> emptyStream() {
        return EmptyStream.getInstance();
    }

    public static <T> Stream<T> singleton(T head) {
        return of(head, emptyStream());
    }

    public static <T> Stream<T> of(T head, Stream<T> tail) {
        return Optional
            .ofNullable(head)
            .map(h -> ConsStream.create(h, tail))
            .orElse(tail);
    }

    public static <T> Stream<T> of(T head, Lazy<Stream<T>> lazyTail) {
        return Optional
            .ofNullable(head)
            .map(h -> LazyTailStream.create(h, lazyTail))
            .orElse(lazyTail.get());
    }

    public static <T> Stream<T> of(T head, Supplier<Stream<T>> getTailFunc) {
        return Optional
            .ofNullable(head)
            .map(h -> LazyTailStream.create(h, Lazy.of(getTailFunc)))
            .orElseGet(getTailFunc);
    }

    @SafeVarargs
    public static <A> Stream<A> of(final A... array) {
        Stream<A> stream = emptyStream();
        if (array != null) {
            for (int idx = array.length - 1; idx >= 0; idx--) {
                if (array[idx] != null) {
                    stream = of(array[idx], stream);
                }
            }
        }

        return stream;
    }

    public static <T> Stream<T> of(final Iterable<T> iterable) {
        return fromIterable(iterable);
    }

    public static <T> Stream<T> fromIterable(final Iterable<T> iterable) {
        if (iterable instanceof Stream) {
            return (Stream<T>) iterable;
        }

        final Stream.Builder<T> builder = new Stream.Builder<>();
        for (T it: iterable) {
            builder.append(it);
        }

        return builder.build();
    }

    public static Stream<Character> of(final CharSequence charSequence) {
        if (charSequence == null) {
            return emptyStream();
        }

        return of(charSequence, 0);
    }

    public static Stream<Integer> range(final int from, final int to) {
        return computeWhile(from, i -> i + 1, i -> i <= to);
    }

    public static Stream<BigInteger> range(final BigInteger from, final BigInteger to) {
        return fromWhile(from, i -> i.compareTo(to) <= 0);
    }

    public static Stream<Integer> from(final int from) {
        return compute(from, i -> i + 1);
    }

    public static Stream<BigInteger> from(final BigInteger from) {
        return compute(from, i -> i.add(BigInteger.valueOf(1)));
    }

    public static Stream<Integer> fromWhile(final int from, final Predicate<Integer> p) {
        return computeWhile(from, i -> i + 1, p);
    }

    public static Stream<BigInteger> fromWhile(final BigInteger from, final Predicate<BigInteger> p) {
        return computeWhile(from, i -> i.add(BigInteger.valueOf(1)), p);
    }

    public static <T> Stream<T> compute(final T initial, Function<T, T> f) {
        return of(initial, () -> compute(f.apply(initial), f));
    }

    public static <T> Stream<T> computeWhile(final T initial, Function<T, T> f, Predicate<T> p) {
        return p.test(initial)
            ? of(initial, () -> computeWhile(f.apply(initial), f, p))
            : emptyStream();
    }

    public static <T> Stream<T> withFilter(
        final Stream<T> baseStream,
        final Predicate<T> predicate,
        final boolean isTrue
    ) {
        return baseStream.nonEmpty()
            ? new FilteredStream<>(baseStream, predicate, isTrue)
            : emptyStream();
    }

    public static <T, R> Stream<R> withMapFunction(
        final Stream<T> baseStream,
        final Function<T, R> mapFunction
    ) {
        return baseStream.nonEmpty()
            ? new MappedStream<>(baseStream, mapFunction)
            : emptyStream();
    }

    public static <A, B> Stream<Pair<A, B>> zipStreams(
        final Stream<A> aStream,
        final Stream<B> bStream
    ) {
        return aStream.nonEmpty() && bStream.nonEmpty()
            ? new ZippedStream<>(aStream, bStream)
            : emptyStream();
    }

    // Private methods

    private static Stream<Character> of(final CharSequence charSequence, final int atPosition) {
        final int length = charSequence.length();
        if (atPosition >= length) {
            return emptyStream();
        }

        return of(charSequence.charAt(atPosition), () -> of(charSequence, atPosition + 1));
    }

    // Private types

    private static final class EmptyStream<T> implements Stream<T> {

        public static final EmptyStream<?> singleton = new EmptyStream<>();

        public static <A> EmptyStream<A> getInstance() {
            @SuppressWarnings("unchecked")
            final EmptyStream<A> emptyStream = (EmptyStream<A>) singleton;

            return emptyStream;
        }

        private EmptyStream() { }

        @Override
        public Optional<T> getHeadOption() {
            return Optional.empty();
        }

        @Override
        public Stream<T> getTail() {
            return this;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean nonEmpty() {
            return false;
        }

        @Override
        public Spliterator<T> spliterator() {
            return new Spliterator<T>() {

                @Override
                public boolean tryAdvance(Consumer<? super T> action) {
                    return false;
                }

                @Override
                public Spliterator<T> trySplit() {
                    return this;
                }

                @Override
                public long estimateSize() {
                    return 0;
                }

                @Override
                public int characteristics() {
                    return Spliterator.NONNULL
                        | Spliterator.SIZED
                        | Spliterator.SUBSIZED
                        | Spliterator.DISTINCT
                        | Spliterator.ORDERED
                        | Spliterator.IMMUTABLE;
                }
            };
        }

        @Override
        public String toString() {
            return "[]";
        }
    }

    private static abstract class NonEmptyStream<T> implements Stream<T> {

        private final T head;

        protected NonEmptyStream(final T head) {
            this.head = head;
        }

        public T getHead() {
            return head;
        }

        @Override
        public Optional<T> getHeadOption() {
            return Optional.ofNullable(head);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean nonEmpty() {
            return true;
        }

        @Override
        public String toString() {

            final int maxCountToShow = 3;

            final Pair<List<String>, Stream<String>> pair = this
                .map(Objects::toString)
                .takeAndDrop(maxCountToShow);

            final List<String> firstItemList = pair.getLeft();

            final boolean hasMoreThanThat = pair.getRight().nonEmpty();

            final String wholeString = hasMoreThanThat ? "[ %s, ...]" : "[ %s ]";

            return String.format(wholeString, String.join(", ", firstItemList));
        }
    }

    private static final class ConsStream<T> extends NonEmptyStream<T> {

        public static <T> Stream<T> create(T head, Stream<T> tail) {
            return new ConsStream<>(head, tail);
        }

        private final Stream<T> tail;

        private ConsStream(T head, Stream<T> tail) {
            super(head);
            this.tail = tail;
        }

        @Override
        public Stream<T> getTail() {
            return tail;
        }
    }

    private static final class LazyTailStream<T> extends NonEmptyStream<T> {

        public static <T> Stream<T> create(T head, Lazy<Stream<T>> lazyTail) {
            return new LazyTailStream<>(head, lazyTail);
        }

        private final Lazy<Stream<T>> lazyTail;

        private LazyTailStream(T head, Lazy<Stream<T>> lazyTail) {
            super(head);
            this.lazyTail = lazyTail;
        }

        @Override
        public Stream<T> getTail() {
            return lazyTail.get();
        }

        public Lazy<Stream<T>> getLazyTail() {
            return lazyTail;
        }

        @Override
        public String toString() {
            return String.format("[ %s, ...]", getHead().toString());
        }
    }

    private static final class FilteredStream<T> implements Stream<T> {

        private final Lazy<Optional<T>> lazyHead;
        private final Lazy<Stream<T>> lazyTail;

        private FilteredStream(final Stream<T> baseStream, final Predicate<T> predicate, final boolean isTrue) {

            final Lazy<Stream<T>> streamWithFirstMatch = Lazy.of(() ->
                baseStream.foldLeftWhile(
                    baseStream,
                    (r, it) -> predicate.test(it) != isTrue,
                    (r, it) -> r.getTail()
                )
            );

            lazyHead = Lazy.of(() -> streamWithFirstMatch.get().getHeadOption());
            lazyTail = Lazy.of(() -> withFilter(streamWithFirstMatch.get().getTail(), predicate, isTrue));
        }

        @Override
        public Optional<T> getHeadOption() {
            return lazyHead.get();
        }

        @Override
        public Stream<T> getTail() {
            return lazyTail.get();
        }
    }

    private static final class MappedStream<T, R> implements Stream<R> {

        private final Lazy<Optional<R>> lazyHead;
        private final Lazy<Stream<R>> lazyTail;

        private MappedStream(final Stream<T> baseStream, final Function<T, R> mapFunction) {
            this.lazyHead = Lazy.of(() -> baseStream.getHeadOption().map(mapFunction));
            lazyTail = Lazy.of(() -> withMapFunction(baseStream.getTail(), mapFunction));
        }

        @Override
        public Optional<R> getHeadOption() {
            return lazyHead.get();
        }

        @Override
        public Stream<R> getTail() {
            return lazyTail.get();
        }
    }

    private static final class ZippedStream<A, B> implements Stream<Pair<A, B>> {

        private final Stream<A> aStream;
        private final Stream<B> bStream;

        private ZippedStream(final Stream<A> aStream, final Stream<B> bStream) {
            this.aStream = aStream;
            this.bStream = bStream;
        }

        @Override
        public Optional<Pair<A, B>> getHeadOption() {
            return aStream
                .getHeadOption()
                .flatMap(a -> bStream
                    .getHeadOption()
                    .map(b -> Pair.of(a, b))
                );
        }

        @Override
        public Stream<Pair<A, B>> getTail() {
            return aStream.getTail().zip(bStream.getTail());
        }
    }
}
