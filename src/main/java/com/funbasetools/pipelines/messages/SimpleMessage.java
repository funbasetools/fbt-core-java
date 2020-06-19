package com.funbasetools.pipelines.messages;

public final class SimpleMessage extends MessageBase {

    public static SimpleMessage of(final String id, final String content) {
        return new SimpleMessage(id, content);
    }

    private final String id;

    private SimpleMessage(final String id, final String content) {
        super(content);
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
