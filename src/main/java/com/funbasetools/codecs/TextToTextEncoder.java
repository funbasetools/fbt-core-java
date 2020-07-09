package com.funbasetools.codecs;

import com.funbasetools.codecs.text.HexText;
import com.funbasetools.security.HashAlgorithm;

import com.funbasetools.security.SecurityUtils;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@FunctionalInterface
public interface TextToTextEncoder extends ToTextEncoder<String> {

    static TextToTextEncoder getMd5Encoder() {
        return getUtf8HashEncoder(SecurityUtils.getMd5());
    }

    static TextToTextEncoder getSha1Encoder() {
        return getUtf8HashEncoder(SecurityUtils.getSha1());
    }

    static TextToTextEncoder getSha256Encoder() {
        return getUtf8HashEncoder(SecurityUtils.getSha256());
    }

    static TextToTextEncoder getSha512Encoder() {
        return getUtf8HashEncoder(SecurityUtils.getSha512());
    }

    static TextToTextEncoder getHashEncoder(
        final HashAlgorithm hashAlgorithm,
        final Charset charset) {

        Objects.requireNonNull(hashAlgorithm);
        Objects.requireNonNull(charset);

        return str -> {
            final byte[] bytes = hashAlgorithm.computeBytesHash(str.getBytes(charset));

            return HexText
                .getEncoder()
                .encode(bytes);
        };
    }

    static TextToTextEncoder getUtf8HashEncoder(final HashAlgorithm hashAlgorithm) {
        return getHashEncoder(hashAlgorithm, StandardCharsets.UTF_8);
    }
}
