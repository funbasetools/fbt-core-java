package com.funbasetools.pm;

import com.funbasetools.pm.internal.PartialMatchResult;
import com.funbasetools.pm.internal.impl.NoMatching;

public final class Matcher {

    public static <T, R> PartialMatchResult<T, R> when(final T expr) {
        return NoMatching.from(expr);
    }

    private Matcher() {
    }
}
