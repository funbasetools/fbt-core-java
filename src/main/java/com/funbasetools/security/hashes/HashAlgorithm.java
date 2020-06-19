package com.funbasetools.security.hashes;

import com.funbasetools.ShouldNotReachThisPointException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@FunctionalInterface
public interface HashAlgorithm {

    byte[] computeHash(ByteArrayInputStream inputStream) throws IOException;

    default byte[] computeHash(byte[] bytes) {

        try (final ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes)) {
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
