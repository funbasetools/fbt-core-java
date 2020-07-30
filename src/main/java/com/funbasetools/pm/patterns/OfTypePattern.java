package com.funbasetools.pm.patterns;

import com.funbasetools.Types;

public class OfTypePattern<A> implements SinglePattern<A> {

    private final Class<A> type;

    OfTypePattern(final Class<A> type) {
        this.type = type;
    }

    @Override
    public boolean match(final Object expr) {
        return Types.is(type, expr);
    }

    @Override
    public A getMatchedArg(final Object expr) {
        return Types.as(type, expr);
    }
}
