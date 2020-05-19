package com.funbasetools.certainties;

import java.util.function.Function;

public class AtLeast<N extends Number>
        extends PartiallyKnown<N> implements NumberKnowable<N> {

    AtLeast(N value) {
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
        else if (other instanceof AtLeast) {
            final double thisValue = get().doubleValue();
            final double otherValue = other.get().doubleValue();

            return Double.compare(thisValue, otherValue);
        }
        else {
            return 0;
        }
    }

    @Override
    public PartiallyKnown<N> transform(Function<N, N> function) {
        return Knowable.atLeast(function.apply(get()));
    }

    @Override
    public String toString() {
        return String.format("AtLeast(%s)", get());
    }
}
