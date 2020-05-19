package com.funbasetools.certainties;

import org.junit.Test;

import static com.funbasetools.certainties.Knowable.*;
import static org.junit.Assert.*;

public class KnowableTest {

    @Test
    public void testUnknownAssertions() {
        // given
        final Object value = new Object();
        final Knowable<Object> alternative = known(new Object());
        final Knowable<Object> unknown = unknown();

        // then
        assertTrue(
            "Unknown.isUnknown() must be true",
            unknown.isUnknown()
        );

        assertFalse(
            "Unknown.isKnown() must be false",
            unknown.isKnown()
        );

        assertFalse(
            "Unknown.isPartiallyKnown() must be false",
            unknown.isPartiallyKnown()
        );

        assertThrows(
            "Unknown.get() is expected to throw an UnsupportedOperationException",
            UnsupportedOperationException.class,
            unknown::get
        );

        assertSame(
            "Unknown.orElse(alternative value) must return the alternative passed value",
            value,
            unknown.orElse(value)
        );

        assertSame(
            "Unknown.orElse(Knowable) must return the alternative passed Knowable",
            alternative,
            unknown.orElse(alternative)
        );

        assertSame(
            "Unknown.orElseGet(Knowable supplier) must return the alternative passed Knowable",
            alternative,
            unknown.orElseGet(() -> alternative)
        );
    }

    @Test
    public void testKnownAssertions() {
        // given
        final Object value = new Object();
        final Knowable<Object> known = known(value);

        // then
        assertFalse(
            "Known.isUnknown() must be false",
            known.isUnknown()
        );

        assertTrue(
            "Known.isKnown() must be true",
            known.isKnown()
        );

        assertFalse(
            "Known.isPartiallyKnown() must be false (because it's fully known)",
            known.isPartiallyKnown()
        );

        assertSame(
            "Known.get() must return the same value passed to it",
            value,
            known.get()
        );

        assertSame(
            "Known.orElse(alternative value) must return its own value",
            value,
            known.orElse(new Object())
        );

        assertSame(
            "Known.orElse(Knowable) must return itself",
            known,
            known.orElse(Knowable.known(new Object()))
        );

        assertSame(
            "Known.orElseGet(Knowable supplier) must return itself",
            known,
            known.orElseGet(() -> Knowable.known(new Object()))
        );
    }

    @Test
    public void testAtLeastAssertions() {
        // given
        final int value = 10;
        final Knowable<Integer> atLeast = atLeast(value);

        // then
        assertFalse(
            "AtLeast.isUnknown() must be false",
            atLeast.isUnknown()
        );

        assertFalse(
            "AtLeast.isKnown() must be false (because it's a partial knowledge)",
            atLeast.isKnown()
        );

        assertTrue(
            "AtLeast.isPartiallyKnown() must be true",
            atLeast.isPartiallyKnown()
        );

        assertSame(
            "AtLeast.get() must return the same value passed to it",
            value,
            atLeast.get()
        );

        assertSame(
            "AtLeast.orElse(alternative value) must return its own value",
            value,
            atLeast.orElse(5)
        );

        assertSame(
            "AtLeast.orElse(Knowable) must return itself",
            atLeast,
            atLeast.orElse(Knowable.known(5))
        );

        assertSame(
            "AtLeast.orElseGet(Knowable supplier) must return itself",
            atLeast,
            atLeast.orElseGet(() -> Knowable.known(5))
        );
    }

    @Test
    public void testAtMostAssertions() {
        // given
        final int value = 10;
        final Knowable<Integer> atMost = atMost(value);

        // then
        assertFalse(
            "AtMost.isUnknown() must be false",
            atMost.isUnknown()
        );

        assertFalse(
            "AtMost.isKnown() must be false (because it's a partial knowledge)",
            atMost.isKnown()
        );

        assertTrue(
            "AtMost.isPartiallyKnown() must be true",
            atMost.isPartiallyKnown()
        );

        assertSame(
            "AtMost.get() must return the same value passed to it",
            value,
            atMost.get()
        );

        assertSame(
            "AtMost.orElse(alternative value) must return its own value",
            value,
            atMost.orElse(5)
        );

        assertSame(
            "AtMost.orElse(Knowable) must return itself",
            atMost,
            atMost.orElse(Knowable.known(5))
        );

        assertSame(
            "AtMost.orElseGet(Knowable supplier) must return itself",
            atMost,
            atMost.orElseGet(() -> Knowable.known(5))
        );
    }

    @Test
    public void testEquality() {
        assertSame(
            unknown(),
            unknown()
        );
        assertEquals(
            known(10),
            known(10)
        );
        assertNotEquals(
            known(5),
            known(10)
        );
        assertEquals(
            atLeast(10),
            atLeast(10)
        );
        assertNotEquals(
            atLeast(5),
            atLeast(10)
        );
        assertEquals(
            atMost(10),
            atMost(10)
        );
        assertNotEquals(
            atMost(5),
            atMost(10)
        );
    }

    @Test
    public void testTransform() {
        assertSame(
            unknown(),
            unknown().transform(v -> new Object())
        );

        assertEquals(
            known(11),
            known(10).transform(v -> v + 1)
        );
        assertEquals(
            atLeast(11),
            atLeast(10).transform(v -> v + 1)
        );
        assertEquals(
            atMost(11),
            atMost(10).transform(v -> v + 1)
        );
    }

    @Test
    public void testCertaintyComparisons() {
        // given
        final Knowable<Integer> unknown = unknown();
        final Knowable<Integer> known10 = known(10);
        final Knowable<Integer> known5 = known(5);
        final Knowable<Integer> atLeast10 = atLeast(10);
        final Knowable<Integer> atLeast5 = atLeast(5);
        final Knowable<Integer> atMost10 = atMost(10);
        final Knowable<Integer> atMost5 = atMost(5);

        // then
        assertTrue(unknown.isEquallyCertainTo(unknown));
        assertTrue(unknown.isLessCertainThan(known10));
        assertTrue(unknown.isLessCertainThan(atLeast5));
        assertTrue(unknown.isLessCertainThan(atMost5));

        assertTrue(known10.isMoreCertainThan(unknown));
        assertTrue(known10.isEquallyCertainTo(known10));
        assertTrue(known10.isEquallyCertainTo(known5));
        assertTrue(known10.isMoreCertainThan(atLeast10));
        assertTrue(known10.isMoreCertainThan(atMost10));

        assertTrue(atLeast5.isMoreCertainThan(unknown));
        assertTrue(atLeast5.isEquallyCertainTo(atLeast5));
        assertTrue(atLeast5.isLessCertainThan(atLeast10));
        assertTrue(atLeast10.isMoreCertainThan(atLeast5));

        assertTrue(atMost5.isMoreCertainThan(unknown));
        assertTrue(atMost5.isEquallyCertainTo(atMost5));
        assertTrue(atMost5.isMoreCertainThan(atMost10));
        assertTrue(atMost10.isLessCertainThan(atMost5));

        assertTrue(atLeast5.isEquallyCertainTo(atMost5));
        assertTrue(atLeast5.isEquallyCertainTo(atMost10));
        assertTrue(atLeast10.isEquallyCertainTo(atMost5));
        assertTrue(atLeast10.isEquallyCertainTo(atMost10));
    }

    @Test
    public void testIsKnownThatIsLessOrEqualsTo() {
        assertTrue(
            isKnownThatIsLessOrEqualsTo(known(10), 10)
        );
        assertTrue(
            isKnownThatIsLessOrEqualsTo(known(9), 10)
        );
        assertTrue(
            isKnownThatIsLessOrEqualsTo(atMost(10), 10)
        );
        assertTrue(
            isKnownThatIsLessOrEqualsTo(atMost(9), 10)
        );

        assertFalse(
            isKnownThatIsLessOrEqualsTo(unknown(), 10)
        );
        assertFalse(
            isKnownThatIsLessOrEqualsTo(known(11), 10)
        );
        assertFalse(
            isKnownThatIsLessOrEqualsTo(atMost(11), 10)
        );
        assertFalse(
            isKnownThatIsLessOrEqualsTo(atLeast(9), 10)
        );
        assertFalse(
            isKnownThatIsLessOrEqualsTo(atLeast(10), 10)
        );
        assertFalse(
            isKnownThatIsLessOrEqualsTo(atLeast(11), 10)
        );
    }
}
