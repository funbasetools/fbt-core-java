package com.funbasetools.pm.internal.impl;

import com.funbasetools.pm.internal.PartialMatchResult;

public abstract class MatchResultBase<T, R> implements PartialMatchResult<T, R> {

    private final T expr;

    protected MatchResultBase(final T expr) {
        this.expr = expr;
    }

    protected T getExpr() {
        return expr;
    }
}
