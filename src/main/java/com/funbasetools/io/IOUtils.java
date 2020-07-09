package com.funbasetools.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.BiConsumer;

public final class IOUtils {

    public static void readAllBytesWithBuffer(
        final InputStream inputStream,
        final BiConsumer<byte[], Integer> bufferedReadConsumer) throws IOException {

        final byte[] buffer = new byte[4096];
        int read;
        while (0 < (read = inputStream.read(buffer))) {
            bufferedReadConsumer.accept(buffer, read);
        }
    }

    private IOUtils() {
    }
}
