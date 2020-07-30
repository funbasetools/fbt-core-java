package com.funbasetools.pm.patterns;

public class AnyPattern implements SinglePattern<Object> {

    @Override
    public boolean match(Object expr) {
        return true;
    }

    @Override
    public Object getMatchedArg(Object expr) {
        return expr;
    }
}
