package com.funbasetools.pm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.funbasetools.Try;
import java.util.function.Function;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

public class PatternMatchingTest {

    @Test(expected = NotMatchingPatternFoundException.class)
    public void testGetWithoutPatternsThrowsException() {
        // given
        final Object str = "some text";

        // then
        Matcher
            .when(str)
            .get();
    }

    @Test
    public void testOrElseWithoutPatternsReturnsOrElse() {
        // given
        final Object str = "some text";

        // when
        final Object res = Matcher
            .when(str)
            .orElse(() -> 10)
            .get();

        // then
        assertSame(10, res);
    }

    @Test
    public void testIsEqualsPatternWhenExprIsEquals() {
        // given
        final Object str = "some text";

        // then
        assertSame(
            10,
            Matcher
                .when(str)
                .is("some text").then(() -> 10)
                .orElse(() -> 20)
                .get()
        );
    }

    @Test
    public void testIsNullPatternWhenExprIsNull() {
        assertSame(
            10,
            Matcher
                .when(null)
                .isNull().then(() -> 10)
                .orElse(() -> 20)
                .get()
        );
    }

    @Test
    public void testIsEqualsPatternWhenExprIsDifferent() {
        // given
        final Object str = "some text";

        // then
        assertSame(
            20,
            Matcher
                .when(str)
                .is("another text").then(() -> 10)
                .orElse(() -> 20)
                .get()
        );
    }

    @Test
    public void testIsClassPatternIsTrue() {
        // given
        final Object str = "some text";

        // then
        assertSame(
            9, // length of str
            Matcher
                .when(str)
                .is(String.class).then(String::length)
                .orElse(() -> 20)
                .get()
        );
    }

    @Test
    public void testIsClassPatternIsFalse() {
        // given
        final Object str = "some text";

        // then
        assertSame(
            20, // or else
            Matcher
                .when(str)
                .is(Integer.class).then(v -> v + 1)
                .orElse(() -> 20)
                .get()
        );
    }

    @Test
    public void testIsClassAndPatternIsTrue() {
        // given
        final Object str = "some text";

        // then
        assertSame(
            9, // length of str
            Matcher
                .when(str)
                .is(String.class).and(s -> s.startsWith("some")).then(String::length)
                .orElse(() -> 20)
                .get()
        );
    }

    @Test
    public void testIsClassAndPatternIsFalse() {
        // given
        final Object str = "some text";

        // then
        assertSame(
            20, // orElse
            Matcher
                .when(str)
                .is(String.class).and(s -> s.startsWith("hello")).then(String::length)
                .orElse(() -> 20)
                .get()
        );
    }

    @Test
    public void testIsSuccessClassPatternIsTrue() {
        // given
        final Object str = Try.success("some text");

        // then
        assertSame(
            9,
            Matcher
                .when(str)
                .isSuccess(String.class).then(String::length)
                .orElse(() -> 20)
                .get()
        );
    }

    @Test
    public void testIsSuccessClassPatternIsFalse() {
        // given
        final Object str = Try.success("some text");

        // then
        assertSame(
            20,
            Matcher
                .when(str)
                .isSuccess(Integer.class).then(a -> 10)
                .orElse(() -> 20)
                .get()
        );
    }

    @Test
    public void testIsSuccessPatternIsTrue() {
        // given
        final Object str = Try.success("some text");

        // then
        assertSame(
            10,
            Matcher
                .when(str)
                .isSuccess("some text").then(() -> 10)
                .orElse(() -> 20)
                .get()
        );
    }

    @Test
    public void testIsSuccessPatternIsFalse() {
        // given
        final Object str = Try.success("some text");

        // then
        assertSame(
            20,
            Matcher
                .when(str)
                .isSuccess("another text").then(() -> 10)
                .orElse(() -> 20)
                .get()
        );
    }

    @Test
    public void testIsTupleClassClassPatternIsTrue() {
        // given
        final Object tuple = Pair.of("some text", 100);

        // then
        assertEquals(
            "some text + 100",
            Matcher
                .when(tuple)
                .isTuple(String.class, Integer.class).then((l, r) -> l + " + " + r)
                .orElse(() -> "or else text")
                .get()
        );
    }

    @Test
    public void testIsTupleClassClassPatternIsFalse() {
        // given
        final Object tuple = Pair.of("some text", 100);

        // then
        assertEquals(
            "or else text",
            Matcher
                .when(tuple)
                .isTuple(String.class, Long.class).then((l, r) -> l + " + " + r)
                .orElse(() -> "or else text")
                .get()
        );
    }

    @Test
    public void testIsTupleClassValuePatternIsTrue() {
        // given
        final Object tuple = Pair.of("some text", 100);

        // then
        assertEquals(
            "some text",
            Matcher
                .when(tuple)
                .isTuple(String.class, 100).then(Function.identity())
                .orElse(() -> "or else text")
                .get()
        );
    }

    @Test
    public void testIsTupleClassValuePatternIsFalse() {
        // given
        final Object tuple = Pair.of("some text", 100);

        // then
        assertEquals(
            "or else text",
            Matcher
                .when(tuple)
                .isTuple(String.class, 0).then(Function.identity())
                .orElse(() -> "or else text")
                .get()
        );
        assertEquals(
            "or else text",
            Matcher
                .when(tuple)
                .isTuple(Integer.class, 100).then(Function.identity())
                .orElse(() -> "or else text")
                .get()
        );
        assertEquals(
            "or else text",
            Matcher
                .when(tuple)
                .isTuple(Integer.class, 0).then(Function.identity())
                .orElse(() -> "or else text")
                .get()
        );
    }

    @Test
    public void testIsTupleValueClassPatternIsTrue() {
        // given
        final Object tuple = Pair.of("some text", 100);

        // then
        assertEquals(
            100,
            Matcher
                .when(tuple)
                .isTuple("some text", Integer.class).then(Function.identity())
                .orElse(() -> 0)
                .get()
        );
    }

    @Test
    public void testIsTupleValueClassPatternIsFalse() {
        // given
        final Object tuple = Pair.of("some text", 100);

        // then
        assertEquals(
            0,
            Matcher
                .when(tuple)
                .isTuple("some other text", Integer.class).then(Function.identity())
                .orElse(() -> 0)
                .get()
        );
        assertEquals(
            "or else text",
            Matcher
                .when(tuple)
                .isTuple("some text", String.class).then(Function.identity())
                .orElse(() -> "or else text")
                .get()
        );
        assertEquals(
            "or else text",
            Matcher
                .when(tuple)
                .isTuple("some other text", String.class).then(Function.identity())
                .orElse(() -> "or else text")
                .get()
        );
    }

    @Test
    public void testIsTupleValueValuePatternIsTrue() {
        // given
        final Object tuple = Pair.of("some text", 100);

        // then
        assertEquals(
            "then text",
            Matcher
                .when(tuple)
                .isTuple("some text", 100).then(() -> "then text")
                .orElse(() -> "or else text")
                .get()
        );
    }

    @Test
    public void testIsTupleValueValuePatternIsFalse() {
        // given
        final Object tuple = Pair.of("some text", 100);

        // then
        assertEquals(
            "or else text",
            Matcher
                .when(tuple)
                .isTuple("some other text", 100).then(() -> "then text")
                .orElse(() -> "or else text")
                .get()
        );
        assertEquals(
            "or else text",
            Matcher
                .when(tuple)
                .isTuple("some text", 0).then(() -> "then text")
                .orElse(() -> "or else text")
                .get()
        );
        assertEquals(
            "or else text",
            Matcher
                .when(tuple)
                .isTuple("some other text", 0).then(() -> "then text")
                .orElse(() -> "or else text")
                .get()
        );
    }
}
