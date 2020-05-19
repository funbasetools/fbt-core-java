package com.funbasetools.certainties;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class NotUnknown<T> implements Knowable<T> {

    private final T value;

    protected NotUnknown(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public boolean isUnknown() {
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(this.getClass())
            .append(this.get())
            .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NotUnknown &&
            new EqualsBuilder()
                .append(this.getClass(), obj.getClass())
                .append(this.get(), ((NotUnknown<?>) obj).get())
                .isEquals();
    }
}
