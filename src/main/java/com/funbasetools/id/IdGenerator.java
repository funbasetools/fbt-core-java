package com.funbasetools.id;

@FunctionalInterface
public interface IdGenerator<ID, FROM> {
    ID generate(final FROM obj);
}
