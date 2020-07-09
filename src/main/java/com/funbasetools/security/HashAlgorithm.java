package com.funbasetools.security;

import static com.funbasetools.io.IOUtils.toInputStream;

import com.funbasetools.Algorithm;
import com.funbasetools.ShouldNotReachThisPointException;
import com.funbasetools.Try;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface HashAlgorithm extends Algorithm {

    byte[] computeHash(final InputStream inputStream) throws IOException;

    default byte[] computeBytesHash(final byte[] bytes) {
        final byte[] normalizedBytes = Optional
            .ofNullable(bytes)
            .orElse(new byte[0]);

        return Try
            .of(() -> computeHash(toInputStream(normalizedBytes)))
            .toOptional()
            .orElseThrow(ShouldNotReachThisPointException::new);
    }
}
