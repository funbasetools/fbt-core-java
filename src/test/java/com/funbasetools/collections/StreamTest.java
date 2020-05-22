package com.funbasetools.collections;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.funbasetools.FBT.*;
import static org.junit.Assert.*;

public class StreamTest {

    private final Stream<Integer> naturals = Streams.from(1);
    private final Stream<Integer> factorialStream = factorial();
    private final Stream<Integer> fibonacciStream = fibonacci();

    @Test
    public void testFactorial() {
        // Given
        final Stream<Integer> factorialSample =
            asStream(1, 2, 6, 24, 120, 720, 5040, 40320, 362880);

        // Then
        assertTrue(factorialStream.corresponds(factorialSample));
    }

    @Test
    public void testFibonacci() {
        // Given
        final Stream<Integer> fibSample =
            asStream(1, 1, 2, 3, 5, 8, 13, 21, 34);

        // Then
        assertTrue(fibonacciStream.corresponds(fibSample));
    }

    @Test
    public void testEmptyStream() {
        assertEquals(known(0L), Streams.emptyStream().size());
        assertEquals("[]", Streams.emptyStream().toString());
    }

    @Test
    public void testSingletonStream() {
        assertEquals(known(1L), Streams.singleton("abc").size());
        assertEquals("[ abc ]", Streams.singleton("abc").toString());
    }

    @Test
    public void testStringStream() {
        // given
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";

        // when
        final Stream<Character> charStream = asStream(alphabet);

        //then
        assertEquals(known((long)alphabet.length()), charStream.size());
        assertArrayEquals(
            new Object[] { 'a', 'b', 'c', 'd', 'e' },
            charStream.take(5).toArray()
        );
    }

    @Test
    public void testAppend() {
        // given
        final Stream<Integer> baseStream = asStream(0, 1, 2);

        // when
        final Stream<Integer> stream = baseStream.append(3);

        // Then
        assertEquals(atLeast(1L), stream.size());
        assertEquals("[ 0, ...]", stream.toString());
    }

    @Test
    public void testPrepend() {
        // given
        final Stream<Integer> baseStream = asStream(2, 3);

        // when
        final Stream<Integer> stream1 = baseStream.prepend(1);
        final Stream<Integer> stream2 = stream1.prepend(0);

        // then
        assertEquals(known(3L), stream1.size());
        assertEquals("[ 1, 2, 3 ]", stream1.toString());
        assertEquals(known(4L), stream2.size());
        assertEquals("[ 0, 1, 2, ...]", stream2.toString());
    }

    @Test
    public void testFiltering() {
        // given
        final Stream<Integer> stream = Streams.from(1);

        // when
        final Stream<Integer> filteredStream = stream.filter(v -> v % 2 == 0);

        // then
        assertArrayEquals(
            new Object[]{ 2, 4, 6, 8, 10 },
            filteredStream.take(5).toArray()
        );
    }

    @Test
    public void testMapping() {
        // given
        final Stream<Integer> stream = Streams.from(1);

        // when
        final Stream<Double> mappedStream = stream.map(v -> v * 5 / 2.0);

        // then
        assertArrayEquals(
            new Object[]{ 2.5, 5.0, 7.5, 10.0, 12.5 },
            mappedStream.take(5).toArray()
        );
    }

    @Test
    public void testFlatMapping() {
        // given
        final Stream<Integer> stream = Streams.from(1);
        final Function<Integer, List<Integer>> f = v ->
            (v % 3 == 0)
                ? Arrays.asList(v, v * v, v * v * v)
                : Collections.emptyList();

        // when
        final Stream<Integer> flatMappedStream = stream.flatMap(f); // filter to get even numbers

        // then
        assertArrayEquals(
            new Object[]{ 3, 9, 27, 6, 36, 216 },
            flatMappedStream.take(6).toArray()
        );
    }

    @Test
    public void testZipping() {
        // given
        final Stream<Integer> aStream = Streams.from(1);
        final Stream<Character> bStream = asStream(ArrayUtils.toObject("abcdefghijklmnopqrstuvwxyz".toCharArray()));

        // when
        final Stream<Pair<Integer, Character>> pairedStream = aStream.zip(bStream);

        // then
        assertArrayEquals(
            new Object[] { pair(1, 'a'), pair(2, 'b'), pair(3, 'c') },
            pairedStream.take(3).toArray()
        );
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

        assertEquals(known(10L), Streams.range(1, 10).size());
        assertEquals(known(9L), Streams.range(1, 10).drop(1).size());
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
