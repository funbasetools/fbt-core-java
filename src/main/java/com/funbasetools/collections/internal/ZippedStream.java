package com.funbasetools.collections.internal;

import com.funbasetools.collections.Stream;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;

public final class ZippedStream<A, B> implements Stream<Pair<A, B>> {

    private final Stream<A> aStream;
    private final Stream<B> bStream;

    public static <A, B> Stream<Pair<A, B>> of(final Stream<A> aStream, final Stream<B> bStream) {
        return new ZippedStream<>(aStream, bStream);
    }

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
