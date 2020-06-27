package com.funbasetools;

import com.funbasetools.codecs.text.HexText;
import com.funbasetools.codecs.zip.ZipEncoder;
import com.funbasetools.collections.Stream;
import com.funbasetools.collections.Streams;
import com.funbasetools.io.Writer;
import com.funbasetools.security.hashes.HashAlgorithm;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;

public final class Functions {

    public static <I, O> Function<I, O> of(final Function<I, O> f) {
        return f;
    }

    public static Consumer<byte[]> getWriterTo(final String resource, final Writer writer) {
        return bytes -> writer.writeAllBytes(resource, bytes);
    }

    public static Function<byte[], String> getHexHashGenerator(final HashAlgorithm hashAlgorithm) {
        return of(hashAlgorithm::computeBytesHash)
            .andThen(of(HexText.getEncoder()::encode));
    }

    public static Function<List<Pair<String, byte[]>>, byte[]> getBytesZipDeflater(final ZipEncoder zipEncoder) {
        final Function<List<Pair<String, byte[]>>, Stream<Pair<String, byte[]>>> streamOf = Streams::of;
        final Function<Stream<Pair<String, byte[]>>, byte[]> encode = zipEncoder::encode;

        return streamOf.andThen(encode);
    }

    private Functions() {
    }
}
