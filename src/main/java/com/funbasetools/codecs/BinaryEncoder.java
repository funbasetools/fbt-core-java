package com.funbasetools.codecs;

import static com.funbasetools.io.IOUtils.toInputStream;

import com.funbasetools.ShouldNotReachThisPointException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface BinaryEncoder extends ToBinaryEncoder<byte[]> {

    @Override
    default void encodeTo(final byte[] source, final OutputStream outputStream) {
        try {
            encode(toInputStream(source), outputStream);
        }
        catch (IOException ex) {
            throw new ShouldNotReachThisPointException(ex);
        }
    }

    void encode(final InputStream inputStream, final OutputStream outputStream) throws IOException;
}
