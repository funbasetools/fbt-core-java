package com.funbasetools.pm.patterns;

import java.util.Objects;

public class EqualsToPattern<A> implements SinglePattern<A> {

    private final A value;

    EqualsToPattern(A value) {
        this.value = value;
    }

    @Override
    public boolean match(final Object expr) {
        return Objects.equals(value, expr);
    }

    @Override
    public A getMatchedArg(final Object expr) {
        @SuppressWarnings("unchecked")
        final A arg = (A)expr;
        return arg;
    }
}
