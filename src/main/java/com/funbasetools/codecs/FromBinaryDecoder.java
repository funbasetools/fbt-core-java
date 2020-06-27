package com.funbasetools.codecs;

import com.funbasetools.ShouldNotReachThisPointException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public interface FromBinaryDecoder<TARGET> extends Decoder<byte[], TARGET> {

    @Override
    default TARGET decode(final byte[] bytes) {
        try (final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            return decode(byteArrayInputStream);
        }
        catch (IOException ex) {
            throw new ShouldNotReachThisPointException(ex);
        }
    }

    TARGET decode(final InputStream inputStream) throws IOException;
}
