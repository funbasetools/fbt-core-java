package com.funbasetools.collections;

import com.funbasetools.Lazy;
import com.funbasetools.collections.internal.ConsStream;
import com.funbasetools.collections.internal.EmptyStream;
import com.funbasetools.collections.internal.FilteredStream;
import com.funbasetools.collections.internal.LazyTailStream;
import com.funbasetools.collections.internal.MappedStream;
import com.funbasetools.collections.internal.ZippedStream;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;

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

    public static <T> Stream<T> of(final java.util.stream.Stream<T> stream) {
        return new Stream<>() {
            @Override
            public Optional<T> getHeadOption() {
                return stream.findFirst();
            }

            @Override
            public Stream<T> getTail() {
                return isEmpty()
                    ? emptyStream()
                    : of(stream.skip(1));
            }
        };
    }

    public static <T> Stream<T> fromIterable(final Iterable<T> iterable) {
        if (iterable instanceof Stream) {
            return (Stream<T>) iterable;
        }

        return fromIterator(iterable.iterator());
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
            ? FilteredStream.of(baseStream, predicate, isTrue)
            : emptyStream();
    }

    public static <T, R> Stream<R> withMapFunction(
        final Stream<T> baseStream,
        final Function<T, R> mapFunction
    ) {
        return baseStream.nonEmpty()
            ? MappedStream.of(baseStream, mapFunction)
            : emptyStream();
    }

    public static <A, B> Stream<Pair<A, B>> zipStreams(
        final Stream<A> aStream,
        final Stream<B> bStream
    ) {
        return aStream.nonEmpty() && bStream.nonEmpty()
            ? ZippedStream.of(aStream, bStream)
            : emptyStream();
    }

    // Private methods

    private static <T> Stream<T> fromIterator(final Iterator<T> iterator) {
        return iterator.hasNext()
            ? of(iterator.next(), () -> fromIterator(iterator))
            : emptyStream();
    }

    private static Stream<Character> of(final CharSequence charSequence, final int atPosition) {
        final int length = charSequence.length();
        if (atPosition >= length) {
            return emptyStream();
        }

        return of(charSequence.charAt(atPosition), () -> of(charSequence, atPosition + 1));
    }
}
