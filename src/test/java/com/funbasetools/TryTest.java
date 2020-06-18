package com.funbasetools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class TryTest {

    @Test
    public void testSuccess() {
        // given
        final Object result = new Object();
        final Try<Object> res = Try.success(result);

        // then
        testSucceeded(res, result);
    }

    @Test
    public void testFailure() {
        // given
        final Exception ex = new Exception();
        final Try<Object> res = Try.failure(ex);

        // then
        testFailed(res, ex);
    }

    @Test
    public void succeededTry() {
        // given
        final Object result = new Object();
        final Try<Object> res = Try.of(() -> result);

        // then
        assertTrue(res.isSuccess());
        assertFalse(res.isFailure());
        assertTrue(res.toOptional().isPresent());
        assertFalse(res.toFailureOptional().isPresent());
        assertEquals(result, res.toOptional().get());
    }

    @Test
    public void testFailedTry() {
        // given
        final Exception ex = new Exception();
        final Try<Object> res = Try.of(() -> {
            throw ex;
        });

        // then
        testFailed(res, ex);
    }

    @Test
    public void testMapSuccess() {
        // given
        final Object r1 = new Object();
        final Object r2 = new Object();
        final Try<Object> res1 = Try.success(r1);
        final ThrowingFunction<Object, Object, ?> f = ignored -> r2;

        // when
        final Try<Object> res2 = res1.map(f);

        // then
        testSucceeded(res2, r2);
    }

    @Test
    public void testMapFailureBeforeMap() {
        // given
        final Exception ex1 = new Exception();
        final Try<Object> res1 = Try.failure(ex1);

        @SuppressWarnings("unchecked")
        final ThrowingFunction<Object, Object, NullPointerException> failingFunction =
            (ThrowingFunction<Object, Object, NullPointerException>)mock(ThrowingFunction.class);

        doThrow(new NullPointerException())
            .when(failingFunction)
            .apply(any());

        // when
        final Try<Object> res2 = res1.map(failingFunction);

        // then
        testFailed(res2, ex1);
        verify(failingFunction, never()).apply(any());
    }

    @Test
    public void testMapFailureInMap() {
        // given
        final Try<Object> res1 = Try.success(new Object());
        final NullPointerException ex = new NullPointerException();
        @SuppressWarnings("unchecked")
        final ThrowingFunction<Object, Object, NullPointerException> failingFunction =
            (ThrowingFunction<Object, Object, NullPointerException>)mock(ThrowingFunction.class);

        doThrow(ex)
            .when(failingFunction)
            .apply(any());

        // when
        final Try<Object> res2 = res1.map(failingFunction);

        // then
        testFailed(res2, ex);
        verify(failingFunction, times(1)).apply(any());
    }

    // private methods

    private <T> void testSucceeded(final Try<T> res, T result) {
        assertTrue(res.isSuccess());
        assertFalse(res.isFailure());
        assertTrue(res.toOptional().isPresent());
        assertFalse(res.toFailureOptional().isPresent());
        assertEquals(result, res.toOptional().get());
    }

    private void testFailed(final Try<?> res, final Exception ex) {
        assertFalse(res.isSuccess());
        assertTrue(res.isFailure());
        assertFalse(res.toOptional().isPresent());
        assertTrue(res.toFailureOptional().isPresent());
        assertEquals(ex, res.toFailureOptional().get());
    }
}
