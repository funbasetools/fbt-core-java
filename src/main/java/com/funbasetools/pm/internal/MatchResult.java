package com.funbasetools.pm.internal;

import com.funbasetools.Try;
import java.util.Optional;

public interface MatchResult<R> {
    R get();

    default Optional<R> toOptional() {
        return Try
            .of(this::get)
            .toOptional();
    }
}
