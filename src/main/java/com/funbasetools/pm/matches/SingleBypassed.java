package com.funbasetools.pm.matches;

import com.funbasetools.pm.statements.CompletedStatement;
import com.funbasetools.pm.statements.MatchStatement;
import java.util.function.Function;
import java.util.function.Predicate;

public class SingleBypassed<EXPR, A, R> implements SingleResult<EXPR, A, R> {

    private final R result;

    public SingleBypassed(final R result) {
        this.result = result;
    }

    @Override
    public MatchStatement<EXPR, R> then(final Function<A, R> f) {
        return new CompletedStatement<>(result);
    }

    @Override
    public SingleResult<EXPR, A, R> and(final Predicate<A> p) {
        return this;
    }
}
