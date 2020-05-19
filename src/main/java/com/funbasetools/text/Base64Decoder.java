package com.funbasetools.text;

import java.util.Base64;

public enum Base64Decoder implements TextDecoder {
    STANDARD(Base64.getDecoder()),
    MIME(Base64.getMimeDecoder()),
    URL(Base64.getUrlDecoder());

    private final Base64.Decoder innerDecoder;

    Base64Decoder(Base64.Decoder innerDecoder) {
        this.innerDecoder = innerDecoder;
    }

    @Override
    public byte[] decode(String src) {
        return innerDecoder.decode(src);
    }
}
