package com.funbasetools.pm.patterns;

public final class Patterns {

    public static SinglePattern<Object> any() {
        return new AnyPattern();
    }

    public static <A> SinglePattern<A> eq(final A value) {
        return new EqualsToPattern<>(value);
    }

    public static SinglePattern<Object> nul() {
        return eq(null);
    }

    public static <A, B> DoublePattern<A, B> pair(final SinglePattern<A> aPattern, final SinglePattern<B> bPattern) {
        return new PairPattern<>(aPattern, bPattern);
    }

    public static <A> SinglePattern<A> ofType(final Class<A> aClass) {
        return new OfTypePattern<>(aClass);
    }

    public static <A> SinglePattern<A> succ(final SinglePattern<A> pattern) {
        return new IsSuccessPattern<>(pattern);
    }

    private Patterns() {
    }
}
