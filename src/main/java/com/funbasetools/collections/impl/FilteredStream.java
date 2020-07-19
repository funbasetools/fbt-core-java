package com.funbasetools.collections.impl;

import com.funbasetools.collections.Stream;
import java.util.function.Predicate;

public final class FilteredStream<T> extends LazyTailStream<T> {

    public static <T> Stream<T> of(final Stream<T> baseStream, final Predicate<T> predicate, final boolean isTrue) {
        return ofLazy(baseStream, predicate, isTrue);
    }

    private static <T> LazyStream<T> ofLazy(final Stream<T> baseStream, final Predicate<T> predicate, final boolean isTrue) {
        return LazyStream
            .of(() -> {
                final var streamWithFirstMatch = baseStream
                    .foldLeftWhile(
                        baseStream,
                        (stream, it) -> predicate.test(it) != isTrue,
                        ((stream, it) -> stream.getTail())
                    );

                return streamWithFirstMatch.nonEmpty()
                    ? new FilteredStream<>(streamWithFirstMatch, predicate, isTrue)
                    : Stream.empty();
            });
    }

    private FilteredStream(final Stream<T> streamWithFirstMatch, final Predicate<T> predicate, final boolean isTrue) {
        super(
            streamWithFirstMatch.getHeadOption().orElse(null),
            ofLazy(streamWithFirstMatch.getTail(), predicate, isTrue)
        );
    }
}
