package com.funbasetools.pm;

import com.funbasetools.pm.internal.PartialMatchResult;
import com.funbasetools.pm.internal.impl.NoMatching;
import java.util.Map;
import org.apache.commons.lang3.tuple.Pair;

public final class Matcher {

    public static <T, R> PartialMatchResult<T, R> when(final T expr) {
        return NoMatching.from(expr);
    }

    public static <A, B, R> PartialMatchResult<Map.Entry<A, B>, R> when(final A aExpr, final B bExpr) {
        return when(Pair.of(aExpr, bExpr));
    }

    private Matcher() {
    }
}
