package com.funbasetools.pm.matches;

import com.funbasetools.pm.statements.CompletedStatement;
import com.funbasetools.pm.statements.MatchStatement;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class DoubleBypassed<EXPR, A, B, R> implements DoubleResult<EXPR, A, B, R> {

    private final R result;

    public DoubleBypassed(final R result) {
        this.result = result;
    }

    @Override
    public MatchStatement<EXPR, R> then(final BiFunction<A, B, R> f) {
        return new CompletedStatement<>(result);
    }

    @Override
    public DoubleResult<EXPR, A, B, R> and(final BiPredicate<A, B> p) {
        return this;
    }
}
