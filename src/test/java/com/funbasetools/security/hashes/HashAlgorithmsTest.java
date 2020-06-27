package com.funbasetools.security.hashes;

import com.funbasetools.codecs.text.HexText;
import org.junit.Test;

import static org.junit.Assert.*;

public class HashAlgorithmsTest {

    @Test
    public void testEmptyDataCrc32Hash() {
        // given
        final byte[] data = new byte[0];
        final HashAlgorithm hashAlgorithm = new Crc32HashAlgorithm();
        final String expectedHash = "00000000";

        // when
        final byte[] hash = hashAlgorithm.computeBytesHash(data);

        // then
        assertEquals(
            String.format("Hash should be equal to %s", expectedHash),
            expectedHash,
            HexText.getEncoder().encode(hash)
        );
    }

    @Test
    public void testEmptyDataMd5Hash() {
        // given
        final byte[] data = new byte[0];
        final HashAlgorithm hashAlgorithm = new Md5HashAlgorithm();
        final String expectedHash = "d41d8cd98f00b204e9800998ecf8427e";

        // when
        final byte[] hash = hashAlgorithm.computeBytesHash(data);

        // then
        assertEquals(
            String.format("Hash should be equal to %s", expectedHash),
            expectedHash,
            HexText.getEncoder().encode(hash)
        );
    }

    @Test
    public void testEmptyDataSha1Hash() {
        // given
        final byte[] data = new byte[0];
        final HashAlgorithm hashAlgorithm = new Sha1HashAlgorithm();
        final String expectedHash = "da39a3ee5e6b4b0d3255bfef95601890afd80709";

        // when
        final byte[] hash = hashAlgorithm.computeBytesHash(data);

        // then
        assertEquals(
            String.format("Hash should be equal to %s", expectedHash),
            expectedHash,
            HexText.getEncoder().encode(hash)
        );
    }

    @Test
    public void testEmptyDataSha256Hash() {
        // given
        final byte[] data = new byte[0];
        final HashAlgorithm hashAlgorithm = new Sha256HashAlgorithm();
        final String expectedHash = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

        // when
        final byte[] hash = hashAlgorithm.computeBytesHash(data);

        // then
        assertEquals(
            String.format("Hash should be equal to %s", expectedHash),
            expectedHash,
            HexText.getEncoder().encode(hash)
        );
    }

    @Test
    public void testEmptyDataSha512Hash() {
        // given
        final byte[] data = new byte[0];
        final HashAlgorithm hashAlgorithm = new Sha512HashAlgorithm();
        final String expectedHash = "cf83e1357eefb8bdf1542850d66d8007d620e4050b5715dc83f4a921d36ce9ce47d0d13c5d85f2b0ff8318d2877eec2f63b931bd47417a81a538327af927da3e";

        // when
        final byte[] hash = hashAlgorithm.computeBytesHash(data);

        // then
        assertEquals(
            String.format("Hash should be equal to %s", expectedHash),
            expectedHash,
            HexText.getEncoder().encode(hash)
        );
    }
}
