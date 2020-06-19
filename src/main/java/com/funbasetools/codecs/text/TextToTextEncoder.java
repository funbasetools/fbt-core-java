package com.funbasetools.codecs.text;

import com.funbasetools.codecs.Encoder;
import com.funbasetools.security.hashes.HashAlgorithm;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@FunctionalInterface
public interface TextToTextEncoder extends Encoder<String, String> {

    static TextToTextEncoder getMd5Encoder() {
        return getUtf8HashEncoder(HashAlgorithm.getMd5());
    }

    static TextToTextEncoder getSha1Encoder() {
        return getUtf8HashEncoder(HashAlgorithm.getSha1());
    }

    static TextToTextEncoder getSha256Encoder() {
        return getUtf8HashEncoder(HashAlgorithm.getSha256());
    }

    static TextToTextEncoder getSha512Encoder() {
        return getUtf8HashEncoder(HashAlgorithm.getSha512());
    }

    static TextToTextEncoder getHashEncoder(
        final HashAlgorithm hashAlgorithm,
        final Charset charset) {

        Objects.requireNonNull(hashAlgorithm);
        Objects.requireNonNull(charset);

        return str -> {
            final byte[] bytes = hashAlgorithm.computeHash(str.getBytes(charset));

            return HexText
                .getEncoder()
                .encode(bytes);
        };
    }

    static TextToTextEncoder getUtf8HashEncoder(final HashAlgorithm hashAlgorithm) {
        return getHashEncoder(hashAlgorithm, StandardCharsets.UTF_8);
    }
}
