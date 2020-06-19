package com.funbasetools.pipelines.messages;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Optional;

public abstract class MessageBase implements Message {

    private final String content;

    public String getContent() {
        return content;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(Message.class)
            .append(getId())
            .build();
    }

    @Override
    public boolean equals(Object obj) {
        return
            obj instanceof Message
            && new EqualsBuilder()
                .append(getId(), ((Message)obj).getId())
                .build();
    }

    protected MessageBase(final String content) {
        this.content = Optional.ofNullable(content).orElse("");
    }
}
