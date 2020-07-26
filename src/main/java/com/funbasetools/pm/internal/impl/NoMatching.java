package com.funbasetools.pm.internal.impl;

import com.funbasetools.Try;
import com.funbasetools.Types;
import com.funbasetools.collections.Stream;
import com.funbasetools.pm.internal.FinalCondition;
import com.funbasetools.pm.internal.PartialBiCondition;
import com.funbasetools.pm.internal.PartialMatchResult;
import com.funbasetools.pm.NotMatchingPatternFoundException;
import com.funbasetools.pm.internal.PartialCondition;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import org.apache.commons.lang3.tuple.Pair;

public final class NoMatching<EXPR, R> extends MatchResultBase<EXPR, R> {

    public static <EXPR, R> NoMatching<EXPR, R> from(final EXPR expr) {
        return new NoMatching<>(expr);
    }

    private NoMatching(final EXPR expr) {
        super(expr);
    }

    @Override
    public R get() {
        throw new NotMatchingPatternFoundException();
    }

    @Override
    public PartialMatchResult<EXPR, R> orElse(final Supplier<R> supplier) {
        return MatchFound
            .from(supplier.get());
    }

    @Override
    public <T> PartialCondition<EXPR, T, R> is(final Class<T> type) {
        return PartialConditionImpl
            .from(
                getExpr(),
                expr -> Types.is(type, expr),
                expr -> Types.as(type, expr)
            );
    }

    @Override
    public <T> FinalCondition<EXPR, R> is(final T obj) {
        return FinalConditionImpl
            .from(
                getExpr(),
                obj,
                Objects::equals
            );
    }

    @Override
    public FinalCondition<EXPR, R> isNull() {
        return is((Object)null);
    }

    @Override
    public <T> PartialCondition<EXPR, T, R> isSuccess(final Class<T> resultType) {
        return PartialConditionImpl
            .from(
                getExpr(),
                expr -> Types.isSuccess(resultType, expr),
                expr -> ((Try<?>)expr)
                    .toOptional()
                    .map(e -> Types.as(resultType, e))
                    .orElse(null)
            );
    }

    @Override
    public <T> FinalCondition<EXPR, R> isSuccess(final T result) {
        return FinalConditionImpl
            .from(
                getExpr(),
                result,
                (expr, res) -> Optional
                    .ofNullable(Types.as(Try.class, expr))
                    .flatMap(Try::toOptional)
                    .filter(e -> Objects.equals(e, res))
                    .isPresent()
            );
    }

    @Override
    public <A, B> PartialBiCondition<EXPR, A, B, R> isTuple(final Class<A> left, final Class<B> right) {
        return PartialBiConditionImpl
            .from(
                getExpr(),
                expr -> Types.isTuple(left, right, expr),
                expr -> Optional
                    .ofNullable(Types.as(Map.Entry.class, expr))
                    .map(tuple -> Pair.of(Types.as(left, tuple.getKey()), Types.as(right, tuple.getValue())))
                    .orElse(null)
            );
    }

    @Override
    public <A, B> PartialCondition<EXPR, A, R> isTuple(final Class<A> left, final B right) {
        return PartialConditionImpl
            .from(
                getExpr(),
                expr -> Types.isTuple(left, Object.class, expr)
                    && Objects.equals(right, ((Map.Entry<?, ?>)expr).getValue()),
                expr -> Optional
                    .ofNullable(Types.as(Map.Entry.class, expr))
                    .map(tuple -> Types.as(left, tuple.getKey()))
                    .orElse(null)
            );
    }

    @Override
    public <A, B> PartialCondition<EXPR, B, R> isTuple(final A left, final Class<B> right) {
        return PartialConditionImpl
            .from(
                getExpr(),
                expr -> Types.isTuple(Object.class, right, expr)
                    && Objects.equals(left, ((Map.Entry<?, ?>)expr).getKey()),
                expr -> Optional
                    .ofNullable(Types.as(Map.Entry.class, expr))
                    .map(tuple -> Types.as(right, tuple.getValue()))
                    .orElse(null)
            );
    }

    @Override
    public <A, B> FinalCondition<EXPR, R> isTuple(final A left, final B right) {
        return FinalConditionImpl
            .from(
                getExpr(),
                Pair.of(left, right),
                (expr, res) -> Optional
                    .ofNullable(Types.as(Map.Entry.class, expr))
                    .filter(tuple ->
                        Objects.equals(tuple.getKey(), res.getKey())
                        && Objects.equals(tuple.getValue(), res.getValue())
                    )
                    .isPresent()
            );
    }
}
