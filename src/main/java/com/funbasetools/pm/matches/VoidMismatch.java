package com.funbasetools.pm.matches;

import com.funbasetools.pm.statements.ContinuingStatement;
import com.funbasetools.pm.statements.MatchStatement;
import java.util.function.Supplier;

public class VoidMismatch<EXPR, R> implements VoidResult<EXPR, R> {

    private final EXPR expr;

    public VoidMismatch(final EXPR expr) {
        this.expr = expr;
    }

    @Override
    public MatchStatement<EXPR, R> then(final Supplier<R> f) {
        return new ContinuingStatement<>(expr);
    }
}
