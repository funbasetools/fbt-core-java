package com.funbasetools.pm.statements;

import com.funbasetools.pm.matches.DoubleResult;
import com.funbasetools.pm.matches.DoubleBypassed;
import com.funbasetools.pm.matches.SingleResult;
import com.funbasetools.pm.matches.SingleBypassed;
import com.funbasetools.pm.matches.VoidBypassed;
import com.funbasetools.pm.matches.VoidResult;
import com.funbasetools.pm.patterns.DoublePattern;
import com.funbasetools.pm.patterns.Pattern;
import com.funbasetools.pm.patterns.SinglePattern;
import java.util.Optional;
import java.util.function.Supplier;

public class CompletedStatement<EXPR, R> implements MatchStatement<R> {

    private final R result;

    public CompletedStatement(final R result) {
        this.result = result;
    }

    @Override
    public R get() {
        return result;
    }

    @Override
    public Optional<R> toOptional() {
        return Optional.ofNullable(result);
    }

    @Override
    public VoidResult<R> is(final Pattern pattern) {
        return new VoidBypassed<>(result);
    }

    @Override
    public <A> SingleResult<A, R> is(final SinglePattern<A> pattern) {
        return new SingleBypassed<>(result);
    }

    @Override
    public <A, B> DoubleResult<A, B, R> is(final DoublePattern<A, B> pattern) {
        return new DoubleBypassed<>(result);
    }

    @Override
    public R orElse(final Supplier<R> supplier) {
        return result;
    }
}
