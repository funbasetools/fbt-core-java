package com.funbasetools.security.hashes;

import com.funbasetools.BytesUtil;

import java.util.zip.CRC32;

public class Crc32HashAlgorithm extends HashAlgorithmBase {

    @Override
    protected Object initHash() {
        return new CRC32();
    }

    @Override
    protected void updateHash(final Object hash, final byte[] buffer, final int length) {
        ((CRC32)hash).update(buffer, 0, length);
    }

    @Override
    protected byte[] getHash(final Object hash) {
        final int hashValue = (int)((CRC32)hash).getValue();
        return BytesUtil.toByteArray(hashValue);
    }
}
