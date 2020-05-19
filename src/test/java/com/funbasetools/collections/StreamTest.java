package com.funbasetools.collections;

import com.funbasetools.certainties.Knowable;
import org.junit.Test;

import static org.junit.Assert.*;

public class StreamTest {

    private final Stream<Integer> naturals = Streams.from(1);
    private final Stream<Integer> factorialStream = factorial();
    private final Stream<Integer> fibonacciStream = fibonacci();

    @Test
    public void testFactorial() {
        // Given
        final Stream<Integer> factorialSample =
            Streams.of(1, 2, 6, 24, 120, 720, 5040, 40320, 362880);

        // Then
        assertTrue(factorialStream.corresponds(factorialSample));
    }

    @Test
    public void testFibonacci() {
        // Given
        final Stream<Integer> fibSample =
            Streams.of(1, 1, 2, 3, 5, 8, 13, 21, 34);

        // Then
        assertTrue(fibonacciStream.corresponds(fibSample));
    }

    @Test
    public void testEmptyStream() {
        assertEquals(Knowable.known(0L), Streams.emptyStream().size());
        assertEquals("[]", Streams.emptyStream().toString());
    }

    @Test
    public void testSingletonStream() {
        assertEquals(Knowable.known(1L), Streams.singleton("abc").size());
        assertEquals("[ abc ]", Streams.singleton("abc").toString());
    }

    @Test
    public void testAppend() {
        // given
        final Stream<Integer> baseStream = Streams.of(0, 1, 2);

        // when
        final Stream<Integer> stream = baseStream.append(3);

        // Then
        assertEquals(Knowable.atLeast(1L), stream.size());
        assertEquals("[ 0, ...]", stream.toString());
    }

    @Test
    public void testPrepend() {
        // given
        final Stream<Integer> baseStream = Streams.of(2, 3);

        // when
        final Stream<Integer> stream1 = baseStream.prepend(1);
        final Stream<Integer> stream2 = stream1.prepend(0);

        // then
        assertEquals(Knowable.known(3L), stream1.size());
        assertEquals("[ 1, 2, 3 ]", stream1.toString());
        assertEquals(Knowable.known(4L), stream2.size());
        assertEquals("[ 0, 1, 2, ...]", stream2.toString());
    }

    @Test
    public void testRangeStream() {
        assertArrayEquals(
            new Object[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
            Streams.range(1, 10).takeWhile(it -> true).toArray()
        );
        assertArrayEquals(
            new Object[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 },
            Streams.fromWhile(1, i -> i < 10).takeWhile(it -> true).toArray()
        );

        assertEquals(Knowable.known(10L), Streams.range(1, 10).size());
        assertEquals(Knowable.known(9L), Streams.range(1, 10).drop(1).size());
    }

    @Test
    public void testToStringOfComputedStream() {
        assertEquals(
            "[ 0 ]",
            Streams.singleton(0).toString()
        );

        assertEquals(
            "[ 0, 1 ]",
            Streams.of(0, 1).toString()
        );

        assertEquals(
            "[ 0, 1, 2 ]",
            Streams.of(0, 1, 2).toString()
        );

        assertEquals(
            "[ 0, 1, 2, ...]",
            Streams.of(0, 1, 2, 3).toString()
        );
    }

    // private methods

    private Stream<Integer> factorial() {
        return Streams.of(
            1,
            () ->
                factorialStream
                    .zip(naturals.getTail())
                    .map(t -> t.getLeft() * t.getRight())
        );
    }

    private Stream<Integer> fibonacci() {
        return Streams
            .of(1, 1)
            .append(() ->
                fibonacciStream
                    .zip(fibonacciStream.getTail())
                    .map(t -> t.getLeft() + t.getRight())
            );

    }

}
