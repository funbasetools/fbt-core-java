package com.funbasetools.collections.impl;

import com.funbasetools.collections.Stream;
import org.apache.commons.lang3.tuple.Pair;

public final class ZippedStream<A, B> extends LazyTailStream<Pair<A, B>> {

    public static <A, B> Stream<Pair<A, B>> of(final Stream<A> aStream, final Stream<B> bStream) {
        return aStream.nonEmpty() && bStream.nonEmpty()
            ? new ZippedStream<>(aStream, bStream)
            : Stream.empty();
    }

    private ZippedStream(final Stream<A> aStream, final Stream<B> bStream) {
        super(
            Pair.of(
                aStream.getHeadOption().orElse(null),
                bStream.getHeadOption().orElse(null)
            ),
            LazyStream.of(() -> of(aStream.getTail(), bStream.getTail()))
        );
    }
}
