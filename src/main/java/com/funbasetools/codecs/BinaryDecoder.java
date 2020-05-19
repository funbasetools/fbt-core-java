package com.funbasetools.codecs;

import java.io.*;

public interface BinaryDecoder extends FromBinaryDecoder<byte[]> {

    @Override
    default byte[] decode(final InputStream inputStream) throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        decode(inputStream, outputStream);

        return outputStream.toByteArray();
    }

    void decode(final InputStream inputStream, final OutputStream outputStream) throws IOException;
}
