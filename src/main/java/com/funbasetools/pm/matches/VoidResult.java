package com.funbasetools.pm.matches;

import com.funbasetools.pm.statements.MatchStatement;
import java.util.function.Supplier;

public interface VoidResult<EXPR, R> {

    MatchStatement<EXPR, R> then(final Supplier<R> f);
}
