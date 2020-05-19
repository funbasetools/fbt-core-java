package com.funbasetools.security.hashes;

import java.nio.ByteBuffer;
import java.util.zip.CRC32;

public class Crc32HashAlgorithm extends HashAlgorithmBase {

    @Override
    protected Object initHash() {
        return new CRC32();
    }

    @Override
    protected void updateHash(Object hash, byte[] buffer, int length) {
        ((CRC32)hash).update(buffer, 0, length);
    }

    @Override
    protected byte[] getHash(Object hash) {
        final int hashValue = (int)((CRC32)hash).getValue();
        return ByteBuffer.allocate(Integer.BYTES).putInt(hashValue).array();
    }
}
