package com.funbasetools.collections;

public interface TailGrowable<T, TG extends TailGrowable<T, TG>> {

    TG append(final T item);

    TG append(final TG tailGrowable);
}
