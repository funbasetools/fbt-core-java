package com.funbasetools.collections.internal;

import com.funbasetools.collections.Stream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.tuple.Pair;

public abstract class NonEmptyStream<T> implements Stream<T> {

    private final T head;

    protected NonEmptyStream(final T head) {
        this.head = head;
    }

    public T getHead() {
        return head;
    }

    @Override
    public Optional<T> getHeadOption() {
        return Optional.ofNullable(head);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean nonEmpty() {
        return true;
    }

    @Override
    public String toString() {

        final int maxCountToShow = 3;

        final Pair<List<String>, Stream<String>> pair = this
            .map(Objects::toString)
            .takeAndDrop(maxCountToShow);

        final List<String> firstItemList = pair.getLeft();

        final boolean hasMoreThanThat = pair.getRight().nonEmpty();

        final String wholeString = hasMoreThanThat ? "[ %s, ...]" : "[ %s ]";

        return String.format(wholeString, String.join(", ", firstItemList));
    }
}
