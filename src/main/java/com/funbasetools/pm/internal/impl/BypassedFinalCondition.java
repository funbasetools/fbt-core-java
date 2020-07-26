package com.funbasetools.pm.internal.impl;

import com.funbasetools.pm.internal.FinalCondition;
import com.funbasetools.pm.internal.PartialMatchResult;
import java.util.function.Supplier;

public class BypassedFinalCondition<EXPR, R> implements FinalCondition<EXPR, R> {

    private final MatchFound<EXPR, R> matchFound;

    public static <EXPR, R> BypassedFinalCondition<EXPR, R> from(final MatchFound<EXPR, R> matchFound) {
        return new BypassedFinalCondition<>(matchFound);
    }

    private BypassedFinalCondition(final MatchFound<EXPR, R> matchFound) {
        this.matchFound = matchFound;
    }

    @Override
    public PartialMatchResult<EXPR, R> then(Supplier<R> f) {
        return matchFound;
    }
}
