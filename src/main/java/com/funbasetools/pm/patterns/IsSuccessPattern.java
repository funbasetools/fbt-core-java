package com.funbasetools.pm.patterns;

import com.funbasetools.Try;
import com.funbasetools.Types;
import java.util.Optional;

public class IsSuccessPattern<A> implements SinglePattern<A> {

    private final SinglePattern<A> pattern;

    public IsSuccessPattern(SinglePattern<A> pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean match(final Object expr) {
        return Optional
            .<Try<?>>ofNullable(Types.as(Try.class, expr))
            .flatMap(Try::toOptional)
            .filter(pattern::match)
            .isPresent();
    }

    @Override
    public A getMatchedArg(final Object expr) {
        return ((Try<?>)expr)
            .toOptional()
            .map(pattern::getMatchedArg)
            .orElse(null);
    }
}
