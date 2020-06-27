package com.funbasetools.pipes;

import java.io.Serializable;
import org.apache.commons.lang3.tuple.Pair;

public class Message<T extends Serializable> extends Pair<String, T> {

    private final String id;
    private final T body;

    public static <T extends Serializable> Message<T> of(final String id, final T body) {
        return new Message<>(id, body);
    }

    protected Message(final String id, final T body) {
        this.id = id;
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public T getBody() {
        return body;
    }

    @Override
    public String getLeft() {
        return id;
    }

    @Override
    public T getRight() {
        return body;
    }

    @Override
    public T setValue(T value) {
        throw new UnsupportedOperationException("Cannot change value of immutable object");
    }
}
