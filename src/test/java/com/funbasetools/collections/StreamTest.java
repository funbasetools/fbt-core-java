package com.funbasetools.collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

public class StreamTest {

    private final Stream<Integer> naturals = Streams.from(1);
    private final Stream<Integer> factorialStream = factorial();
    private final Stream<Integer> fibonacciStream = fibonacci();

    @Test
    public void testFactorial() {
        // Given
        final Stream<Integer> factorialSample =
            Streams.ofNullable(1, 2, 6, 24, 120, 720, 5040, 40320, 362880);

        // Then
        assertTrue(factorialStream.corresponds(factorialSample));
    }

    @Test
    public void testFibonacci() {
        // Given
        final Stream<Integer> fibSample =
            Streams.ofNullable(1, 1, 2, 3, 5, 8, 13, 21, 34);

        // Then
        assertTrue(fibonacciStream.corresponds(fibSample));
    }

    @Test
    public void testEmptyStream() {
        assertEquals("[]", Stream.empty().toString());
    }

    @Test
    public void testBuilder() {
        // given
        final Stream.Builder<Integer> builder = new Stream.Builder<>();

        // when
        final Stream<Integer> stream = builder
            .append(1)
            .append(2)
            .append(3)
            .build();

        // then
        assertArrayEquals(
            new Object[] { 1, 2, 3 },
            stream.take(100).toArray()
        );
    }

    @Test
    public void testCreateStreamWithNulls() {
        // given
        final Stream<Integer> stream = Streams.of(1, 2, 3, null, 5, null);

        // when
        final List<Integer> list = stream.take(10);

        // then
        assertEquals(4, list.size());
        assertEquals(Integer.valueOf(1), list.get(0));
        assertEquals(Integer.valueOf(2), list.get(1));
        assertEquals(Integer.valueOf(3), list.get(2));
        assertEquals(Integer.valueOf(5), list.get(3));
    }

    @Test
    public void testCreateNullableStreamWithNulls() {
        // given
        final Stream<Integer> stream = Streams.ofNullable(1, 2, 3, null, 5, null);

        // when
        final List<Integer> list = stream.take(10);

        // then
        assertEquals(6, list.size());
        assertEquals(Integer.valueOf(1), list.get(0));
        assertEquals(Integer.valueOf(2), list.get(1));
        assertEquals(Integer.valueOf(3), list.get(2));
        assertNull(list.get(3));
        assertEquals(Integer.valueOf(5), list.get(4));
        assertNull(list.get(5));
    }

    @Test
    public void testSingletonStream() {
        assertEquals("[ abc ]", Streams.singleton("abc").toString());
    }

    @Test
    public void testStringStream() {
        // given
        final String alphabet = "abcdefghijklmnopqrstuvwxyz";

        // when
        final Stream<Character> charStream = Streams.of(alphabet);

        //then
        assertArrayEquals(
            new Object[] { 'a', 'b', 'c', 'd', 'e' },
            charStream.take(5).toArray()
        );
    }

    @Test
    public void testAppend() {
        // given
        final Stream<Integer> baseStream = Streams.ofNullable(0, 1, 2);

        // when
        final Stream<Integer> stream = baseStream.append(3);

        // Then
        assertEquals("[ 0, ...]", stream.toString());
    }

    @Test
    public void testCastTo() {
        // given
        final Stream<Object> objStream = Streams.of(1, 2, 3, 4, 5);

        // when
        final Stream<Integer> intStream = objStream.castTo(Integer.class);

        // then
        assertArrayEquals(
            new Integer[] { 1, 2, 3, 4, 5 },
            intStream.take(10).toArray()
        );
    }

    @Test
    public void testExist() {
        // given
        final Stream<Integer> stream = Streams.ofNullable(0, 2, 4, 6, 8);

        // then
        assertTrue(stream.exist(v -> v == 2));
        assertTrue(stream.existNot(v -> v == 3));

        assertFalse(stream.exist(v -> v == 3));
        assertFalse(stream.existNot(v -> v == 8));
    }

    @Test
    public void testFilter() {
        // given
        final Stream<Integer> stream = Streams.range(1, 20);

        // when
        final Stream<Integer> evens = stream.filter(v -> v % 2 == 0);
        final Stream<Integer> odds = stream.filterNot(v -> v % 2 == 0);

        // then
        assertTrue(evens.corresponds(Streams.ofNullable(2, 4, 6, 8, 10, 12, 14, 16, 18, 20)));
        assertTrue(odds.corresponds(Streams.ofNullable(1, 3, 5, 7, 9, 11, 13, 15, 17, 19)));
    }

    @Test
    public void testFirst() {
        // given
        final Stream<Integer> stream = Streams.range(1, 20);

        // when
        final Optional<Integer> firstEven = stream.first(v -> v % 2 == 0);
        final Optional<Integer> firstOdd = stream.firstNot(v -> v % 2 == 0);

        // then
        assertTrue(firstEven.isPresent());
        assertSame(2, firstEven.get());
        assertTrue(firstOdd.isPresent());
        assertSame(1, firstOdd.get());
    }

    @Test
    public void testPrepend() {
        // given
        final Stream<Integer> baseStream = Streams.ofNullable(2, 3);

        // when
        final Stream<Integer> stream1 = baseStream.prepend(1);
        final Stream<Integer> stream2 = stream1.prepend(0);

        // then
        assertEquals("[ 1, 2, 3 ]", stream1.toString());
        assertEquals("[ 0, 1, 2, ...]", stream2.toString());
    }

    @Test
    public void testPrependStream() {
        // given
        final Stream<Integer> baseStream = Streams.ofNullable(2, 3);

        // when
        final Stream<Integer> result = baseStream.prepend(Streams.ofNullable(0, 1));

        // then
        assertTrue(
            result.corresponds(Streams.ofNullable(0, 1, 2, 3))
        );
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
    public void testMappingWithNulls() {
        // given
        final Stream<Integer> stream = Streams.ofNullable(1, 2, 3, null, 5, null);

        // when
        final Stream<String> mappedStream = stream.map(v -> v != null && v % 2 != 0 ? v.toString() : null);
        final List<String> list = mappedStream.take(10);

        // then
        assertArrayEquals(
            new String[]{ "1", null, "3", null, "5", null },
            list.toArray()
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
        final Stream<Integer> flatMappedStream = stream.flatMap(f);

        // then
        assertArrayEquals(
            new Object[]{ 3, 9, 27, 6, 36, 216 },
            flatMappedStream.take(6).toArray()
        );
    }

    @Test
    public void testFlatMappingOptional() {
        // given
        final Stream<Integer> stream = Streams.from(1);
        final Function<Integer, Optional<Integer>> f = v ->
            (v % 3 == 0)
                ? Optional.of(v)
                : Optional.empty();

        // when
        final Stream<Integer> flatMappedStream = stream.flatMapOptional(f);

        // then
        assertArrayEquals(
            new Object[]{ 3, 6, 9, 12, 15, 18, 21 },
            flatMappedStream.take(7).toArray()
        );
    }

    @Test
    public void testZipping() {
        // given
        final Stream<Integer> aStream = Streams.from(1);
        final Stream<Character> bStream = Streams.ofNullable(ArrayUtils.toObject("abcdefghijklmnopqrstuvwxyz".toCharArray()));

        // when
        final Stream<Pair<Integer, Character>> pairedStream = aStream.zip(bStream);

        // then
        assertArrayEquals(
            new Object[] { Pair.of(1, 'a'), Pair.of(2, 'b'), Pair.of(3, 'c') },
            pairedStream.take(3).toArray()
        );
    }

    @Test
    public void testZippingWithIndex() {
        // given
        final Stream<Character> stream = Streams.ofNullable(ArrayUtils.toObject("abcdefghijklmnopqrstuvwxyz".toCharArray()));

        // when
        final Stream<Pair<Character, Integer>> pairedWithIndex = stream.zipWithIndex();

        // then
        assertTrue(
            pairedWithIndex.corresponds(
                Streams.ofNullable(
                    Pair.of('a', 0),
                    Pair.of('b', 1),
                    Pair.of('c', 2),
                    Pair.of('d', 3),
                    Pair.of('e', 4)
                )
            )
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
    }

    @Test
    public void testToStringOfComputedStream() {
        assertEquals(
            "[ 0 ]",
            Streams.singleton(0).toString()
        );

        assertEquals(
            "[ 0, 1 ]",
            Streams.ofNullable(0, 1).toString()
        );

        assertEquals(
            "[ 0, 1, 2 ]",
            Streams.ofNullable(0, 1, 2).toString()
        );

        assertEquals(
            "[ 0, 1, 2, ...]",
            Streams.ofNullable(0, 1, 2, 3).toString()
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
            .ofNullable(1, 1)
            .append(() ->
                fibonacciStream
                    .zip(fibonacciStream.getTail())
                    .map(t -> t.getLeft() + t.getRight())
            );

    }

}
