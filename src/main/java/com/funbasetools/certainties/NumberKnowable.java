package com.funbasetools.certainties;

import com.funbasetools.Numbers;

public interface NumberKnowable<N extends Number> extends Knowable<N> {

    default NumberKnowable<N> add(final NotUnknown<N> newCertainty) {
        if (newCertainty.isKnown()) {
            return (NumberKnowable<N>) transform(v -> Numbers.add(v, newCertainty.get()));
        }

        return (NumberKnowable<N>) Knowable.getMostCertain(this, newCertainty);
    }
}
