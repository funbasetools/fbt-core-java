package com.funbasetools.pm.patterns;

public interface SinglePattern<A> {

    boolean match(final Object expr);

    A getMatchedArg(final Object expr);
}
