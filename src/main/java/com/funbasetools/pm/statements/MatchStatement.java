package com.funbasetools.pm.statements;

import static com.funbasetools.pm.patterns.Patterns.eq;
import static com.funbasetools.pm.patterns.Patterns.ofType;
import static com.funbasetools.pm.patterns.Patterns.pair;
import static com.funbasetools.pm.patterns.Patterns.succ;

import com.funbasetools.pm.matches.DoubleResult;
import com.funbasetools.pm.matches.SingleResult;
import com.funbasetools.pm.patterns.DoublePattern;
import com.funbasetools.pm.patterns.SinglePattern;
import java.util.Optional;
import java.util.function.Supplier;

public interface MatchStatement<EXPR, R> {

    R get();

    Optional<R> toOptional();

    <A> SingleResult<EXPR, A, R> is(final SinglePattern<A> pattern);

    <A, B> DoubleResult<EXPR, A, B, R> is(final DoublePattern<A, B> pattern);

    R orElse(final Supplier<R> supplier);

    default <A> SingleResult<EXPR, A, R> isSuccess(final Class<A> aClass) {
        return is(succ(ofType(aClass)));
    }

    default <A> SingleResult<EXPR, A, R> isSuccess(final A obj) {
        return is(succ(eq(obj)));
    }

    default <A, B> DoubleResult<EXPR, A, B, R> isTuple(final Class<A> aClass, final Class<B> bClass) {
        return is(pair(ofType(aClass), ofType(bClass)));
    }

    default <A, B> DoubleResult<EXPR, A, B, R> isTuple(final Class<A> aClass, final B bObj) {
        return is(pair(ofType(aClass), eq(bObj)));
    }

    default <A, B> DoubleResult<EXPR, A, B, R> isTuple(final A aObj, final Class<B> bClass) {
        return is(pair(eq(aObj), ofType(bClass)));
    }

    default <A, B> DoubleResult<EXPR, A, B, R> isTuple(final A aObj, final B bObj) {
        return is(pair(eq(aObj), eq(bObj)));
    }
}
