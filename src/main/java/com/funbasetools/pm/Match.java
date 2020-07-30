package com.funbasetools.pm;

import com.funbasetools.pm.statements.ContinuingStatement;
import com.funbasetools.pm.statements.MatchStatement;

public final class Match {

    public static <EXPR, R> MatchStatement<EXPR, R> when(final EXPR expr) {
        return new ContinuingStatement<>(expr);
    }

    private Match() {
    }
}
