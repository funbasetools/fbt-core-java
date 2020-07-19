package com.funbasetools.collections.impl;

import com.funbasetools.collections.Stream;
import java.util.function.Function;

public final class MappedStream<R> extends LazyTailStream<R> {

    public static <T, R> Stream<R> of(final Stream<T> baseStream, final Function<? super T, R> mapFunction) {
        return ofLazy(baseStream, mapFunction);
    }

    private static <T, R> LazyStream<R> ofLazy(final Stream<T> baseStream, final Function<? super T, R> mapFunction) {
        return LazyStream.of(() -> baseStream.nonEmpty()
            ? new MappedStream<>(
                mapFunction.apply(baseStream.getHeadOption().orElse(null)),
                ofLazy(LazyStream.of(baseStream::getTail), mapFunction)
            )
            : Stream.empty());
    }

    private MappedStream(final R head, final LazyStream<R> lazyTail) {
        super(head, lazyTail);
    }
}
