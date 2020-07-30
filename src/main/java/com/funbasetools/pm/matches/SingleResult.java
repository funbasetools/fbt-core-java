package com.funbasetools.pm.matches;

import com.funbasetools.pm.statements.MatchStatement;
import java.util.function.Function;
import java.util.function.Predicate;

public interface SingleResult<EXPR, A, R> {

    MatchStatement<EXPR, R> then(final Function<A, R> f);

    SingleResult<EXPR, A, R> and(final Predicate<A> p);
}
