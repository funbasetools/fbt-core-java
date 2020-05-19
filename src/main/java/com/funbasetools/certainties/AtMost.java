package com.funbasetools.certainties;

import java.util.function.Function;

public final class AtMost<N extends Number>
        extends PartiallyKnown<N> implements NumberKnowable<N> {

    AtMost(N value) {
        super(value);
    }

    @Override
    public int compareCertainty(Knowable<N> other) {

        if (other.isUnknown()) {
            return 1;
        }
        else if (other.isKnown()) {
            return -1;
        }
        else if (other instanceof AtMost) {
            final double thisValue = get().doubleValue();
            final double otherValue = other.get().doubleValue();

            return Double.compare(otherValue, thisValue);
        }
        else {
            return 0;
        }
    }

    @Override
    public Knowable<N> transform(Function<N, N> function) {
        return Knowable.atMost(function.apply(get()));
    }

    @Override
    public String toString() {
        return String.format("AtMost(%s)", get());
    }
}
