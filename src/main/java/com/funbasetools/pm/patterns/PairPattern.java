package com.funbasetools.pm.patterns;

import com.funbasetools.Types;
import java.util.Map;

public class PairPattern<A, B> implements DoublePattern<A, B> {

    private final SinglePattern<A> aPattern;
    private final SinglePattern<B> bPattern;

    PairPattern(final SinglePattern<A> aPattern, final SinglePattern<B> bPattern) {
        this.aPattern = aPattern;
        this.bPattern = bPattern;
    }

    @Override
    public boolean match(final Object expr) {
        if (Types.is(Map.Entry.class, expr)) {
            final Map.Entry<?, ?> pair = (Map.Entry<?, ?>)expr;
            return aPattern.match(pair.getKey())
                && bPattern.match(pair.getValue());
        }

        return false;
    }

    @Override
    public A getFirstMatchedArg(final Object expr) {
        final Map.Entry<?, ?> pair = (Map.Entry<?, ?>)expr;
        @SuppressWarnings("unchecked")
        final A arg = (A)pair.getKey();
        return arg;
    }

    @Override
    public B getSecondMatchedArg(final Object expr) {
        final Map.Entry<?, ?> pair = (Map.Entry<?, ?>)expr;
        @SuppressWarnings("unchecked")
        final B arg = (B)pair.getValue();
        return arg;
    }
}
