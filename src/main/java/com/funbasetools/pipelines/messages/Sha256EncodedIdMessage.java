package com.funbasetools.pipelines.messages;

import com.funbasetools.codecs.text.TextToTextEncoder;

public class Sha256EncodedIdMessage extends EncodedIdMessage {

    public static Sha256EncodedIdMessage of(final String content) {
        return new Sha256EncodedIdMessage(content);
    }

    protected Sha256EncodedIdMessage(String content) {
        super(content, TextToTextEncoder.getSha256Encoder());
    }
}
