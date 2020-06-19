package com.funbasetools.pipelines.messages;

import com.funbasetools.Lazy;
import com.funbasetools.codecs.text.TextToTextEncoder;

import java.util.Objects;

public abstract class EncodedIdMessage extends MessageBase {

    private final Lazy<String> lazyId;

    @Override
    public String getId() {
        return lazyId.get();
    }

    protected EncodedIdMessage(final String content, final TextToTextEncoder encoder) {
        super(content);
        Objects.requireNonNull(encoder);

        this.lazyId = Lazy.of(() -> encoder.encode(getEncodingKey()));
    }

    protected String getEncodingKey() {
        return getContent();
    }
}
