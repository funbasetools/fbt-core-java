package com.funbasetools.pm.matches;

import com.funbasetools.pm.statements.CompletedStatement;
import com.funbasetools.pm.statements.MatchStatement;
import java.util.function.Supplier;

public class VoidMatch<EXPR, R> implements VoidResult<R> {

    @Override
    public MatchStatement<R> then(final Supplier<R> f) {
        final R res = f.get();
        return new CompletedStatement<>(res);
    }
}
