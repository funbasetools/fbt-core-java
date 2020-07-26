package com.funbasetools.pm.internal.impl;

import com.funbasetools.pm.internal.PartialCondition;
import com.funbasetools.pm.internal.PartialMatchResult;
import java.util.function.Function;
import java.util.function.Predicate;

public class PartialConditionImpl<EXPR, T, R> implements PartialCondition<EXPR, T, R> {

    private final EXPR expr;
    private final Predicate<EXPR> predicate;
    private final com.funbasetools.Function<EXPR, T> mapFunction;

    public static <EXPR, T, R> PartialCondition<EXPR, T, R> from(final EXPR expr,
                                                                 final Predicate<EXPR> predicate,
                                                                 final Function<EXPR, T> mapFunction) {
        return new PartialConditionImpl<>(expr, predicate, mapFunction);
    }

    private PartialConditionImpl(final EXPR expr,
                                 final Predicate<EXPR> predicate,
                                 final Function<EXPR, T> mapFunction) {
        this.expr = expr;
        this.predicate = predicate;
        this.mapFunction = mapFunction::apply;
    }

    @Override
    public PartialCondition<EXPR, T, R> and(final Predicate<? super T> p) {
        if (predicate.test(expr)) {
            final T obj = mapFunction.apply(expr);
            return from(
                expr,
                e -> p.test(obj),
                e -> obj
            );
        }

        return from(expr, e -> false, mapFunction);
    }

    @Override
    public PartialMatchResult<EXPR, R> then(final Function<? super T, R> f) {
        if (predicate.test(expr)) {
            return mapFunction
                .andThen(f)
                .andThen(MatchFound::<EXPR, R>from)
                .apply(expr);
        }

        return NoMatching.from(expr);
    }
}
