package com.funbasetools.pm.internal;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public interface PartialBiCondition<EXPR, A, B, R> {

    PartialBiCondition<EXPR, A, B, R> and(final BiPredicate<? super A, ? super B> p);

    PartialMatchResult<EXPR, R> then(final BiFunction<? super A, ? super B, R> f);
}
