package com.funbasetools.security;

import com.funbasetools.BytesUtil;
import com.funbasetools.Function;
import com.funbasetools.ShouldNotReachThisPointException;
import com.funbasetools.ThrowingFunction;
import com.funbasetools.Try;
import com.funbasetools.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class SecurityUtils {

    public static HashAlgorithm getCrc32() {
        final String name = "CRC32";
        return fromNameFunc(ignored -> new CRC32(), crc32 -> fromChecksum(crc32, name))
            .apply(name);
    }

    public static HashAlgorithm getMd5() {
        return fromNameFunc(MessageDigest::getInstance, SecurityUtils::fromMessageDigest)
            .apply("MD5");
    }

    public static HashAlgorithm getSha1() {
        return fromNameFunc(MessageDigest::getInstance, SecurityUtils::fromMessageDigest)
            .apply("SHA-1");
    }

    public static HashAlgorithm getSha256() {
        return fromNameFunc(MessageDigest::getInstance, SecurityUtils::fromMessageDigest)
            .apply("SHA-256");
    }

    public static HashAlgorithm getSha384() {
        return fromNameFunc(MessageDigest::getInstance, SecurityUtils::fromMessageDigest)
            .apply("SHA-384");
    }

    public static HashAlgorithm getSha512() {
        return fromNameFunc(MessageDigest::getInstance, SecurityUtils::fromMessageDigest)
            .apply("SHA-512");
    }

    public static HashAlgorithm fromChecksum(final Checksum checksum, final String checksumName) {

        return new HashAlgorithm() {
            @Override
            public byte[] computeHash(InputStream inputStream) throws IOException {
                checksum.reset();
                IOUtils.readAllBytesWithBuffer(
                    inputStream,
                    (buffer, read) -> checksum.update(buffer, 0, read)
                );
                return BytesUtil.toByteArray((int)checksum.getValue());
            }

            @Override
            public String getName() {
                return checksumName;
            }
        };
    }

    public static HashAlgorithm fromMessageDigest(final MessageDigest messageDigest) {

        return new HashAlgorithm() {
            @Override
            public byte[] computeHash(InputStream inputStream) throws IOException {
                messageDigest.reset();
                IOUtils.readAllBytesWithBuffer(
                    inputStream,
                    (buffer, read) -> messageDigest.update(buffer, 0, read)
                );
                return messageDigest.digest();
            }

            @Override
            public String getName() {
                return messageDigest.getAlgorithm();
            }
        };
    }

    private static <HA extends HashAlgorithm, A> Function<String, HashAlgorithm> fromNameFunc(
        final ThrowingFunction<String, A, GeneralSecurityException> getAlgorithmFromName,
        final Function<A, HA> getHashAlgorithm) {

        return name -> Try
            .of(() -> getAlgorithmFromName.apply(name))
            .recoverIfFailureWith(GeneralSecurityException.class, gse -> {
                throw new IllegalArgumentException(gse);
            })
            .throwIfFailureWith(IllegalArgumentException.class)
            .toOptional()
            .map(getHashAlgorithm)
            .orElseThrow(ShouldNotReachThisPointException::new);
    }

    private SecurityUtils() {
    }
}
