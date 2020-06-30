package com.funbasetools.collections.internal;

import com.funbasetools.Lazy;
import com.funbasetools.collections.Stream;
import com.funbasetools.collections.Streams;
import java.util.Optional;
import java.util.function.Function;

public final class MappedStream<T, R> implements Stream<R> {

    private final Lazy<Optional<R>> lazyHead;
    private final Lazy<Stream<R>> lazyTail;

    public static <T, R> MappedStream<T, R> of(final Stream<T> baseStream, final Function<T, R> mapFunction) {
        return new MappedStream<>(baseStream, mapFunction);
    }

    private MappedStream(final Stream<T> baseStream, final Function<T, R> mapFunction) {
        this.lazyHead = Lazy.of(() -> baseStream.getHeadOption().map(mapFunction));
        lazyTail = Lazy.of(() -> Streams.withMapFunction(baseStream.getTail(), mapFunction));
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
