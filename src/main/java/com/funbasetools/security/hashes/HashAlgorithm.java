package com.funbasetools.security.hashes;

import com.funbasetools.ShouldNotReachThisPointException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

@FunctionalInterface
public interface HashAlgorithm {

    byte[] computeHash(final ByteArrayInputStream inputStream) throws IOException;

    default byte[] computeHash(final byte[] bytes) {
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

    static HashAlgorithm getMd5() {
        return  new Md5HashAlgorithm();
    }

    static HashAlgorithm getSha1() {
        return new Sha1HashAlgorithm();
    }

    static HashAlgorithm getSha256() {
        return new Sha256HashAlgorithm();
    }

    static HashAlgorithm getSha512() {
        return new Sha512HashAlgorithm();
    }
}
