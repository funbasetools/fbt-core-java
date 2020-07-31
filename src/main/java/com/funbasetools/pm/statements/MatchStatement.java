package com.funbasetools.pm.statements;

import static com.funbasetools.pm.patterns.Patterns.eq;
import static com.funbasetools.pm.patterns.Patterns.ofType;
import static com.funbasetools.pm.patterns.Patterns.pair;
import static com.funbasetools.pm.patterns.Patterns.succ;

import com.funbasetools.pm.matches.DoubleResult;
import com.funbasetools.pm.matches.SingleResult;
import com.funbasetools.pm.matches.VoidResult;
import com.funbasetools.pm.patterns.DoublePattern;
import com.funbasetools.pm.patterns.Pattern;
import com.funbasetools.pm.patterns.SinglePattern;
import java.util.Optional;
import java.util.function.Supplier;

public interface MatchStatement<R> {

    R get();

    Optional<R> toOptional();

    VoidResult<R> is(final Pattern pattern);

    <A> SingleResult<A, R> is(final SinglePattern<A> pattern);

    <A, B> DoubleResult<A, B, R> is(final DoublePattern<A, B> pattern);

    R orElse(final Supplier<R> supplier);

    default <A> SingleResult<A, R> isSuccess(final Class<A> aClass) {
        return is(succ(ofType(aClass)));
    }

    default <A> VoidResult<R> isSuccess(final A obj) {
        return is(succ(eq(obj)));
    }

    default <A, B> DoubleResult<A, B, R> isTuple(final Class<A> aClass, final Class<B> bClass) {
        return is(pair(ofType(aClass), ofType(bClass)));
    }

    default <A, B> SingleResult<A, R> isTuple(final Class<A> aClass, final B bObj) {
        return is(pair(ofType(aClass), eq(bObj)));
    }

    default <A, B> SingleResult<B, R> isTuple(final A aObj, final Class<B> bClass) {
        return is(pair(eq(aObj), ofType(bClass)));
    }

    default <A, B> VoidResult<R> isTuple(final A aObj, final B bObj) {
        return is(pair(eq(aObj), eq(bObj)));
    }
}
