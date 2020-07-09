package com.funbasetools.security;

import com.funbasetools.Algorithm;
import com.funbasetools.ShouldNotReachThisPointException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface HashAlgorithm extends Algorithm {

    byte[] computeHash(final InputStream inputStream) throws IOException;

    default byte[] computeBytesHash(final byte[] bytes) {
        final byte[] normalizedBytes = Optional
            .ofNullable(bytes)
            .orElse(new byte[0]);

        try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(normalizedBytes)) {
            return computeHash(inputStream);
        }
        catch (IOException ex) {
            throw new ShouldNotReachThisPointException(ex);
        }
    }
}
