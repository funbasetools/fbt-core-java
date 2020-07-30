package com.funbasetools.pm.matches;

import com.funbasetools.pm.statements.ContinuingStatement;
import com.funbasetools.pm.statements.MatchStatement;
import java.util.function.Function;
import java.util.function.Predicate;

public class SingleMismatch<EXPR, A, R> implements SingleResult<EXPR, A, R> {

    private final EXPR expr;

    public SingleMismatch(final EXPR expr) {
        this.expr = expr;
    }

    @Override
    public MatchStatement<EXPR, R> then(final Function<A, R> f) {
        return new ContinuingStatement<>(expr);
    }

    @Override
    public SingleResult<EXPR, A, R> and(Predicate<A> p) {
        return this;
    }
}
