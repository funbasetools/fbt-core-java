package com.funbasetools.pm.matches;

import com.funbasetools.pm.statements.CompletedStatement;
import com.funbasetools.pm.statements.MatchStatement;
import java.util.function.Supplier;

public class VoidBypassed<EXPR, R> implements VoidResult<R> {

    private final R result;

    public VoidBypassed(final R result) {
        this.result = result;
    }

    @Override
    public MatchStatement<R> then(final Supplier<R> f) {
        return new CompletedStatement<>(result);
    }
}
