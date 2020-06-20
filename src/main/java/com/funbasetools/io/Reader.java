package com.funbasetools.io;

import com.funbasetools.Try;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public interface Reader<IS extends InputStream> {

    <IOE extends IOException> IS openReadStream(final String resource) throws IOE;

    Try<byte[]> readAllBytes(final String resource);

    default Try<IS> tryOpenReadStream(final String resource) {
        return Try.of(() -> openReadStream(resource));
    }

    default Try<String> readAllText(final String resource, Charset charset) {
        return readAllBytes(resource)
            .map(bytes -> new String(bytes, charset));
    }
}
