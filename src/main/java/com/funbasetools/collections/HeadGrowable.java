package com.funbasetools.collections;

public interface HeadGrowable<T, HG extends HeadGrowable<T, HG>> {

    HG prepend(final T item);

    HG prepend(final HG headGrowable);
}
