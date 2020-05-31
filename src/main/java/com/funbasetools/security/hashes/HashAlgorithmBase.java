package com.funbasetools.security.hashes;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public abstract class HashAlgorithmBase implements HashAlgorithm {

    @Override
    public byte[] computeHash(ByteArrayInputStream inputStream) throws IOException {
        final Object hash = initHash();
        final byte[] buffer = new byte[4096];
        int read;
        while (0 < (read = inputStream.read(buffer))) {
            updateHash(hash, buffer, read);
        }

        return getHash(hash);
    }

    protected abstract Object initHash();
    protected abstract void updateHash(final Object hash, final byte[] buffer, final int length);
    protected abstract byte[] getHash(final Object hash);
}
