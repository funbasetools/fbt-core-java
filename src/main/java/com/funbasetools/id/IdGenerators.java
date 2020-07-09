package com.funbasetools.id;

import static com.funbasetools.Functions.ofNullableFunc;
import static com.funbasetools.Functions.orElseGetFunc;

import com.funbasetools.codecs.text.Base64Encoder;
import com.funbasetools.codecs.text.HexText;
import com.funbasetools.security.HashAlgorithm;

public final class IdGenerators {

    public static IdGenerator<String, byte[]> base64IdGeneratorFrom(
            final HashAlgorithm hashAlgorithm,
            final Base64Encoder encoder) {

        return bytes ->
            ofNullableFunc(byte[].class)
                .andThen(orElseGetFunc(() -> new byte[0]))
                .andThen(hashAlgorithm::computeBytesHash)
                .andThen(encoder::encode)
                .apply(bytes);
    }

    public static IdGenerator<String, byte[]> hexIdGeneratorFrom(final HashAlgorithm hashAlgorithm) {
        return bytes ->
            ofNullableFunc(byte[].class)
                .andThen(orElseGetFunc(() -> new byte[0]))
                .andThen(hashAlgorithm::computeBytesHash)
                .andThen(HexText.getEncoder()::encode)
                .apply(bytes);
    }

    public static UUIDGenerator standardUUIDGenerator() {
        return new UUIDStandardRandomGenerator();
    }

    public static UUIDGenerator timeBasedUUIDGenerator() {
        return new UUIDTimeBasedGenerator();
    }
    
    private IdGenerators() {
    }
}
