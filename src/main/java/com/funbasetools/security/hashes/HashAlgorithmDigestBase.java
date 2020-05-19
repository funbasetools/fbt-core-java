package com.funbasetools.security.hashes;

import java.security.MessageDigest;

public abstract class HashAlgorithmDigestBase extends HashAlgorithmBase {

    @Override
    protected Object initHash() {
        return getMessageDigest();
    }

    @Override
    protected void updateHash(Object hash, byte[] buffer, int length) {
        ((MessageDigest) hash).update(buffer, 0, length);
    }

    @Override
    protected byte[] getHash(final Object hash) {
        return ((MessageDigest)hash).digest();
    }

    protected abstract MessageDigest getMessageDigest();
}
