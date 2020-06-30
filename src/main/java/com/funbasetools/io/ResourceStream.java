package com.funbasetools.io;

import com.funbasetools.ThrowingRunnable;
import com.funbasetools.collections.Stream;
import java.io.Closeable;
import java.io.IOException;

public interface ResourceStream<T> extends Closeable {

    Stream<T> getStream();

    static <T> ResourceStream<T> of(final Stream<T> stream, final ThrowingRunnable<IOException> close) {
        return new ResourceStream<>() {

            @Override
            public Stream<T> getStream() {
                return stream;
            }

            @Override
            public void close() throws IOException {
                close.run();
            }
        };
    }

    static <T> ResourceStream<T> of(final Stream<T> stream) {
        return of(stream, () -> {});
    }

    static <T> ResourceStream<T> empty() {
        return of(Stream.empty());
    }
}
