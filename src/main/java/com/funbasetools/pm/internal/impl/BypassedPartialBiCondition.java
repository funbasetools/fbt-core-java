package com.funbasetools.pm.internal.impl;

import com.funbasetools.pm.internal.PartialBiCondition;
import com.funbasetools.pm.internal.PartialMatchResult;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class BypassedPartialBiCondition<EXPR, A, B, R> implements PartialBiCondition<EXPR, A, B, R> {

    private final MatchFound<EXPR, R> matchFound;

    public static <EXPR, A, B, R> BypassedPartialBiCondition<EXPR, A, B, R> from(final MatchFound<EXPR, R> matchFound) {
        return new BypassedPartialBiCondition<>(matchFound);
    }

    private BypassedPartialBiCondition(final MatchFound<EXPR, R> matchFound) {
        this.matchFound = matchFound;
    }

    @Override
    public PartialBiCondition<EXPR, A, B, R> and(final BiPredicate<? super A, ? super B> p) {
        return this;
    }

    @Override
    public PartialMatchResult<EXPR, R> then(BiFunction<? super A, ? super B, R> f) {
        return matchFound;
    }
}
