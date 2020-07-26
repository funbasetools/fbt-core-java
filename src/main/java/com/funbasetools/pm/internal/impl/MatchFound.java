package com.funbasetools.pm.internal.impl;

import com.funbasetools.collections.Stream;
import com.funbasetools.pm.internal.PartialBiCondition;
import com.funbasetools.pm.internal.FinalCondition;
import com.funbasetools.pm.internal.PartialMatchResult;
import com.funbasetools.pm.internal.PartialCondition;
import java.util.function.Supplier;

public final class MatchFound<EXPR, R> extends MatchResultBase<EXPR, R> {

    private final R result;

    public static <EXPR, R> MatchFound<EXPR, R> from(final R result) {
        return new MatchFound<>(result);
    }

    private MatchFound(final R result) {
        super(null);
        this.result = result;
    }

    @Override
    public R get() {
        return result;
    }

    @Override
    public PartialMatchResult<EXPR, R> orElse(final Supplier<R> supplier) {
        return this;
    }

    @Override
    public <T> PartialCondition<EXPR, T, R> is(final Class<T> type) {
        return BypassedPartialCondition.from(this);
    }

    @Override
    public <T> FinalCondition<EXPR, R> is(final T obj) {
        return BypassedFinalCondition.from(this);
    }

    @Override
    public FinalCondition<EXPR, R> isNull() {
        return BypassedFinalCondition.from(this);
    }

    @Override
    public <T> PartialCondition<EXPR, T, R> isSuccess(final Class<T> resultType) {
        return BypassedPartialCondition.from(this);
    }

    @Override
    public <T> FinalCondition<EXPR, R> isSuccess(final T result) {
        return BypassedFinalCondition.from(this);
    }

    @Override
    public <A, B> PartialBiCondition<EXPR, A, B, R> isTuple(final Class<A> left, final Class<B> right) {
        return BypassedPartialBiCondition.from(this);
    }

    @Override
    public <A, B> PartialCondition<EXPR, A, R> isTuple(final Class<A> left, final B right) {
        return BypassedPartialCondition.from(this);
    }

    @Override
    public <A, B> PartialCondition<EXPR, B, R> isTuple(final A left, final Class<B> right) {
        return BypassedPartialCondition.from(this);
    }

    @Override
    public <A, B> FinalCondition<EXPR, R> isTuple(final A left, final B right) {
        return BypassedFinalCondition.from(this);
    }
}
