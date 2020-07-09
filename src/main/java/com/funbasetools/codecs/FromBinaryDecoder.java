package com.funbasetools.codecs;

import static com.funbasetools.io.IOUtils.toInputStream;

import com.funbasetools.ShouldNotReachThisPointException;
import com.funbasetools.Try;
import java.io.IOException;
import java.io.InputStream;

public interface FromBinaryDecoder<TARGET> extends Decoder<byte[], TARGET> {

    @Override
    default TARGET decode(final byte[] bytes) {
        return Try
            .of(() -> decode(toInputStream(bytes)))
            .toOptional()
            .orElseThrow(ShouldNotReachThisPointException::new);
    }

    TARGET decode(final InputStream inputStream) throws IOException;
}
