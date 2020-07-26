package com.funbasetools.pm.internal;

import java.util.function.Supplier;

public interface FinalCondition<EXPR, R> {

    PartialMatchResult<EXPR, R> then(final Supplier<R> f);
}
