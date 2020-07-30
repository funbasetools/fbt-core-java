package com.funbasetools.pm.matches;

import com.funbasetools.pm.statements.MatchStatement;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public interface DoubleResult<EXPR, A, B, R> {

    MatchStatement<EXPR, R> then(final BiFunction<A, B, R> f);

    DoubleResult<EXPR, A, B, R> and(final BiPredicate<A, B> p);
}
