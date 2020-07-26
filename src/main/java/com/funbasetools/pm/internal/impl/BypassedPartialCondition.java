package com.funbasetools.pm.internal.impl;

import com.funbasetools.pm.internal.PartialCondition;
import com.funbasetools.pm.internal.PartialMatchResult;
import java.util.function.Function;
import java.util.function.Predicate;

public class BypassedPartialCondition<EXPR, T, R> implements PartialCondition<EXPR, T, R> {

    private final MatchFound<EXPR, R> matchFound;

    public static <EXPR, T, R> BypassedPartialCondition<EXPR, T, R> from(final MatchFound<EXPR, R> matchFound) {
        return new BypassedPartialCondition<>(matchFound);
    }

    private BypassedPartialCondition(final MatchFound<EXPR, R> matchFound) {
        this.matchFound = matchFound;
    }

    @Override
    public PartialCondition<EXPR, T, R> and(Predicate<? super T> p) {
        return this;
    }

    @Override
    public PartialMatchResult<EXPR, R> then(Function<? super T, R> f) {
        return matchFound;
    }
}
