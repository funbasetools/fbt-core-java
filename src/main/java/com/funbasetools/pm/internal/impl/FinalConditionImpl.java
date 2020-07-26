package com.funbasetools.pm.internal.impl;

import com.funbasetools.pm.internal.FinalCondition;
import com.funbasetools.pm.internal.PartialMatchResult;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public final class FinalConditionImpl<EXPR, T, R> implements FinalCondition<EXPR, R> {

    private final EXPR expr;
    private final T obj;
    private final BiPredicate<EXPR, T> predicate;

    public static <EXPR, T, R> FinalConditionImpl<EXPR, T, R> from(final EXPR expr,
                                                                   final T obj,
                                                                   final BiPredicate<EXPR, T> predicate) {
        return new FinalConditionImpl<>(expr, obj, predicate);
    }

    private FinalConditionImpl(final EXPR expr,
                               final T obj,
                               final BiPredicate<EXPR, T> predicate) {
        this.expr = expr;
        this.obj = obj;
        this.predicate = predicate;
    }

    @Override
    public PartialMatchResult<EXPR, R> then(final Supplier<R> f) {
        return predicate.test(expr, obj)
            ? MatchFound.from(f.get())
            : NoMatching.from(expr);
    }
}
