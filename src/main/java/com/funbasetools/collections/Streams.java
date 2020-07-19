package com.funbasetools.collections;

import com.funbasetools.collections.impl.ConsStream;
import com.funbasetools.collections.impl.LazyStream;
import com.funbasetools.collections.impl.LazyTailStream;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Streams {

    private Streams() { }

    public static <T> Stream<T> singleton(T head) {
        return of(head, Stream.empty());
    }

    public static <T> Stream<T> of(final T head, final Stream<T> tail) {
        return ConsStream.of(head, tail);
    }

    public static <T> Stream<T> of(final T head, final com.funbasetools.Supplier<Stream<T>> tailSupplier) {
        return Optional
            .ofNullable(head)
            .map(h -> LazyTailStream.of(h, LazyStream.of(tailSupplier)))
            .orElseGet(tailSupplier);
    }

    public static <T> Stream<T> of(T head, Supplier<Stream<T>> getTailFunc) {
        return Optional
            .ofNullable(head)
            .map(h -> LazyTailStream.of(h, LazyStream.of(getTailFunc)))
            .orElseGet(getTailFunc);
    }

    @SafeVarargs
    public static <A> Stream<A> of(final A... array) {
        Stream<A> stream = Stream.empty();
        if (array != null) {
            for (int idx = array.length - 1; idx >= 0; idx--) {
                if (array[idx] != null) {
                    stream = of(array[idx], stream);
                }
            }
        }

        return stream;
    }

    @SafeVarargs
    public static <A> Stream<A> ofNullable(final A... array) {
        Stream<A> stream = Stream.empty();
        if (array != null) {
            for (int idx = array.length - 1; idx >= 0; idx--) {
                stream = of(array[idx], stream);
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
                    ? Stream.empty()
                    : of(stream.skip(1));
            }

            @Override
            public boolean isEmpty() {
                return stream.findAny().isEmpty();
            }

            @Override
            public boolean nonEmpty() {
                return stream.findAny().isPresent();
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
            return Stream.empty();
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
            : Stream.empty();
    }

    // Private methods

    private static <T> Stream<T> fromIterator(final Iterator<T> iterator) {
        return iterator.hasNext()
            ? of(iterator.next(), () -> fromIterator(iterator))
            : Stream.empty();
    }

    private static Stream<Character> of(final CharSequence charSequence, final int atPosition) {
        final int length = charSequence.length();
        if (atPosition >= length) {
            return Stream.empty();
        }

        return of(charSequence.charAt(atPosition), () -> of(charSequence, atPosition + 1));
    }
}
