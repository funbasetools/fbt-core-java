package com.funbasetools.pm.matches;

import com.funbasetools.pm.statements.CompletedStatement;
import com.funbasetools.pm.statements.MatchStatement;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class DoubleMatch<EXPR, A, B, R> implements DoubleResult<EXPR, A, B, R> {

    private final EXPR expr;
    private final A aArg;
    private final B bArg;

    public DoubleMatch(final EXPR expr, final A aArg, final B bArg) {
        this.expr = expr;
        this.aArg = aArg;
        this.bArg = bArg;
    }

    @Override
    public MatchStatement<EXPR, R> then(final BiFunction<A, B, R> f) {
        final R res = f.apply(aArg, bArg);
        return new CompletedStatement<>(res);
    }

    @Override
    public DoubleResult<EXPR, A, B, R> and(final BiPredicate<A, B> p) {
        if (p.test(aArg, bArg)) {
            return this;
        }

        return new DoubleMismatch<>(expr);
    }
}
