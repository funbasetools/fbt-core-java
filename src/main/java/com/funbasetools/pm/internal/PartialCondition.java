package com.funbasetools.pm.internal;

import java.util.function.Function;
import java.util.function.Predicate;

public interface PartialCondition<EXPR, T, R> {

    PartialCondition<EXPR, T, R> and(final Predicate<? super T> p);

    PartialMatchResult<EXPR, R> then(final Function<? super T, R> f);
}
