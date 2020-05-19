package com.funbasetools.certainties;

public abstract class PartiallyKnown<T> extends NotUnknown<T> {

    protected PartiallyKnown(T value) {
        super(value);
    }

    @Override
    public boolean isKnown() {
        return false;
    }

    @Override
    public boolean isPartiallyKnown() {
        return true;
    }
}
