package com.funbasetools.pm.patterns;

import com.funbasetools.Try;
import com.funbasetools.Types;
import java.util.Optional;

public abstract class IsSuccessPatternBase {

    public boolean match(final Object expr) {
        return Optional
            .<Try<?>>ofNullable(Types.as(Try.class, expr))
            .flatMap(Try::toOptional)
            .filter(this::patternMatch)
            .isPresent();
    }

    protected abstract boolean patternMatch(final Object expr);
}
