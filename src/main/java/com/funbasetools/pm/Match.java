package com.funbasetools.pm;

import com.funbasetools.pm.statements.ContinuingStatement;
import com.funbasetools.pm.statements.MatchStatement;
import org.apache.commons.lang3.tuple.Pair;

public final class Match {

    public static <EXPR, R> MatchStatement<EXPR, R> when(final EXPR expr) {
        return new ContinuingStatement<>(expr);
    }

    public static <A, B, R> MatchStatement<Pair<A, B>, R> when(final A left, final B right) {
        return when(Pair.of(left, right));
    }

    private Match() {
    }
}
