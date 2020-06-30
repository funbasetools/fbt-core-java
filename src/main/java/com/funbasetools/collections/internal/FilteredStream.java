package com.funbasetools.collections.internal;

import com.funbasetools.Lazy;
import com.funbasetools.collections.Stream;
import com.funbasetools.collections.Streams;
import java.util.Optional;
import java.util.function.Predicate;

public final class FilteredStream<T> implements Stream<T> {

    private final Lazy<Optional<T>> lazyHead;
    private final Lazy<Stream<T>> lazyTail;

    public static <T> FilteredStream<T> of(final Stream<T> baseStream, final Predicate<T> predicate, final boolean isTrue) {
        return new FilteredStream<>(baseStream, predicate, isTrue);
    }

    private FilteredStream(final Stream<T> baseStream, final Predicate<T> predicate, final boolean isTrue) {

        final Lazy<Stream<T>> streamWithFirstMatch = Lazy.of(() ->
            baseStream.foldLeftWhile(
                baseStream,
                (r, it) -> predicate.test(it) != isTrue,
                (r, it) -> r.getTail()
            )
        );

        lazyHead = Lazy.of(() -> streamWithFirstMatch.get().getHeadOption());
        lazyTail = Lazy.of(() -> Streams.withFilter(streamWithFirstMatch.get().getTail(), predicate, isTrue));
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
