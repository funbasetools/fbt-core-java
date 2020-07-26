package com.funbasetools.pm.internal;

import java.util.function.Supplier;

public interface PartialMatchResult<EXPR, R> extends MatchResult<R> {

    MatchResult<R> orElse(final Supplier<R> supplier);

    <T> PartialCondition<EXPR, T, R> is(final Class<T> type);

    <T> FinalCondition<EXPR, R> is(final T obj);

    FinalCondition<EXPR, R> isNull();

    <T> PartialCondition<EXPR, T, R> isSuccess(final Class<T> resultType);

    <T> FinalCondition<EXPR, R> isSuccess(final T result);

    <A, B> PartialBiCondition<EXPR, A, B, R> isTuple(final Class<A> left, final Class<B> right);

    <A, B> PartialCondition<EXPR, A, R> isTuple(final Class<A> left, final B right);

    <A, B> PartialCondition<EXPR, B, R> isTuple(final A left, final Class<B> right);

    <A, B> FinalCondition<EXPR, R> isTuple(final A left, final B right);

//    <T> PartialBiCondition<EXPR, T, Stream<T>, R> isStreamWith(final T head);
}
