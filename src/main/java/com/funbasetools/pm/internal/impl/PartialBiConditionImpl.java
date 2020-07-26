package com.funbasetools.pm.internal.impl;

import com.funbasetools.Function;
import com.funbasetools.pm.internal.PartialBiCondition;
import com.funbasetools.pm.internal.PartialMatchResult;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class PartialBiConditionImpl<EXPR, A, B, R> implements PartialBiCondition<EXPR, A, B, R> {

    private final EXPR expr;
    private final Predicate<EXPR> predicate;
    private final com.funbasetools.Function<EXPR, ? extends Map.Entry<A, B>> mapFunction;

    public static <EXPR, A, B, R> PartialBiConditionImpl<EXPR, A, B, R> from(final EXPR expr,
                                                                             final Predicate<EXPR> predicate,
                                                                             final Function<EXPR, ? extends Map.Entry<A, B>> mapFunction) {
        return new PartialBiConditionImpl<>(expr, predicate, mapFunction);
    }

    private PartialBiConditionImpl(final EXPR expr,
                                   final Predicate<EXPR> predicate,
                                   final Function<EXPR, ? extends Map.Entry<A, B>> mapFunction) {
        this.expr = expr;
        this.predicate = predicate;
        this.mapFunction = mapFunction;
    }

    @Override
    public PartialBiCondition<EXPR, A, B, R> and(final BiPredicate<? super A, ? super B> p) {
        if (predicate.test(expr)) {
            final Map.Entry<A, B> tuple = mapFunction.apply(expr);
            return from(
                expr,
                e -> p.test(tuple.getKey(), tuple.getValue()),
                e -> tuple
            );
        }

        return from(expr, e -> false, mapFunction);
    }

    @Override
    public PartialMatchResult<EXPR, R> then(final BiFunction<? super A, ? super B, R> f) {
        if (predicate.test(expr)) {
            return mapFunction
                .andThen(tuple -> f.apply(tuple.getKey(), tuple.getValue()))
                .andThen(MatchFound::<EXPR, R>from)
                .apply(expr);
        }

        return NoMatching.from(expr);
    }
}
