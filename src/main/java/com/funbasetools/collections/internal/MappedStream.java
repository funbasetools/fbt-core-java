package com.funbasetools.collections.internal;

import com.funbasetools.Lazy;
import com.funbasetools.collections.Stream;
import java.util.Optional;
import java.util.function.Function;
import org.apache.commons.lang3.tuple.Pair;

public final class MappedStream<T, R> implements Stream<R> {

    private final Lazy<Pair<Optional<R>, Lazy<Stream<R>>>> lazyHeadAndTail;

    public static <T, R> MappedStream<T, R> of(final Stream<T> baseStream, final Function<T, R> mapFunction) {
        return new MappedStream<>(baseStream, mapFunction);
    }

    private MappedStream(final Stream<T> baseStream, final Function<T, R> mapFunction) {
        lazyHeadAndTail = Lazy.of(() -> {
            Optional<R> currHead = baseStream.getHeadOption().map(mapFunction);
            Stream<T> currStream = baseStream;
            while (currHead.isEmpty() && currStream.nonEmpty()) {
                currStream = currStream.getTail();
                currHead = currStream.getHeadOption().map(mapFunction);
            }

            final Stream<T> tail = currStream;
            return Pair.of(currHead, Lazy.of(() -> of(tail.getTail(), mapFunction))) ;
        });
    }

    @Override
    public Optional<R> getHeadOption() {
        return lazyHeadAndTail.get().getLeft();
    }

    @Override
    public Stream<R> getTail() {
        return lazyHeadAndTail.get().getRight().get();
    }
}
