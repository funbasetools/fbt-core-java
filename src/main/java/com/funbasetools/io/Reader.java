package com.funbasetools.io;

import com.funbasetools.Try;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@FunctionalInterface
public interface Reader<IN extends InputStream> {

    IN openReadStream(final String resource) throws IOException;

    default Try<byte[]> readAllBytes(final String resource) {
        return Try.of(() -> {
            try (final IN inputStream = openReadStream(resource)) {
                return IOUtils.toByteArray(inputStream);
            }
        });
    }

    default Try<String> readAllText(final String resource, Charset charset) {
        return readAllBytes(resource)
            .map(bytes -> new String(bytes, charset));
    }
}
