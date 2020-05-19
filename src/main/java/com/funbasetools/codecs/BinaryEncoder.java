package com.funbasetools.codecs;

import com.funbasetools.ShouldNotReachThisPointException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface BinaryEncoder extends ToBinaryEncoder<byte[]> {

    @Override
    default void encodeTo(final byte[] source, final OutputStream outputStream) {
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(source);
            encode(inputStream, outputStream);
        }
        catch (IOException ex) {
            throw new ShouldNotReachThisPointException(ex);
        }
    }

    void encode(final InputStream inputStream, final OutputStream outputStream) throws IOException;
}
