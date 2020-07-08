package com.funbasetools;

import static com.funbasetools.Function.of;

import com.funbasetools.codecs.zip.ZipEncoder;
import com.funbasetools.collections.Stream;
import com.funbasetools.collections.Streams;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;

public final class Functions {

    public static <T> Function<T, Optional<T>> ofNullableFunc(final Class<T> ignored) {
        return of(Optional::ofNullable);
    }

    public static <T, R> Function<Optional<T>, Optional<R>> mapFunc(final java.util.function.Function<T, R> f) {
        return of(opt -> opt.map(f));
    }

    public static <T> Function<Optional<T>, T> orElseGetFunc(final java.util.function.Supplier<T> defaultFunc) {
        return opt -> opt.orElseGet(defaultFunc);
    }

    public static Function<List<Pair<String, byte[]>>, byte[]> getBytesZipDeflater(final ZipEncoder zipEncoder) {
        final Function<List<Pair<String, byte[]>>, Stream<Pair<String, byte[]>>> streamOf = Streams::of;
        final Function<Stream<Pair<String, byte[]>>, byte[]> encode = zipEncoder::encode;

        return streamOf.andThen(encode);
    }

    private Functions() {
    }
}
