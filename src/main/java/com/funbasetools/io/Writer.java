package com.funbasetools.io;

import com.funbasetools.Try;
import com.funbasetools.Unit;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

@FunctionalInterface
public interface Writer {

    OutputStream openWriteStream(final String resource) throws IOException;

    default Try<Unit> writeAll(final String resource, final InputStream inputStream) {
        return Try.of(() -> {
            try (final OutputStream outputStream = openWriteStream(resource)) {
                IOUtils.copyLarge(inputStream, outputStream);
            }
        });
    }

    default Try<Unit> writeAllBytes(final String resource, final byte[] bytes) {
        return Try.flatten(
            Try.of(() -> {
                try (final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
                    return writeAll(resource, byteArrayInputStream);
                }
            })
        );
    }

    default Try<Unit> writeAllText(final String resource, final String text, final Charset charset) {
        return writeAllBytes(resource, text.getBytes(charset));
    }
}
