package com.funbasetools.pm.matches;

import com.funbasetools.pm.statements.CompletedStatement;
import com.funbasetools.pm.statements.MatchStatement;
import java.util.function.Supplier;

public class VoidMatch<EXPR, R> implements VoidResult<EXPR, R> {

    @Override
    public MatchStatement<EXPR, R> then(final Supplier<R> f) {
        final R res = f.get();
        return new CompletedStatement<>(res);
    }
}
