package com.funbasetools.collections.internal;

import com.funbasetools.collections.Stream;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.Consumer;

public final class EmptyStream<T> implements Stream<T> {

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
        return new Spliterator<>() {

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
