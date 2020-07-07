package com.funbasetools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.function.Supplier;
import org.junit.Test;

public class LazyTest {

    @Test
    public void testGet() {
        // given
        final Object res = new Object();

        // then
        assertEquals(
            res,
            Lazy.of(() -> res).get()
        );
    }

    @Test
    public void testIsComputed() {
        // given
        final Lazy<Integer> lazyInt = Lazy.of(() -> 10);

        // when
        lazyInt.get();

        // then
        assertTrue(lazyInt.isComputed());
    }

    @Test
    public void testIsNotComputed() {
        // given
        final Lazy<Integer> lazyInt = Lazy.of(() -> 10);

        // then
        assertFalse(lazyInt.isComputed());
    }

    @Test
    public void testGetIfComputed() {
        // given
        final Lazy<Integer> lazyInt = Lazy.of(() -> 10);

        // when
        lazyInt.get();
        final Optional<Integer> opt = lazyInt.getIfComputed();

        // then
        assertTrue(opt.isPresent());
        assertSame(10, opt.get());
    }

    @Test
    public void testGetIfNotComputed() {
        // given
        final Lazy<Integer> lazyInt = Lazy.of(() -> 10);

        // when
        final Optional<Integer> opt = lazyInt.getIfComputed();

        // then
        assertFalse(opt.isPresent());
    }

    @Test
    public void testCallsToSupplierBeforeBeingComputed() {
        // given
        @SuppressWarnings("unchecked")
        final Supplier<Integer> supplierMock = (Supplier<Integer>)mock(Supplier.class);
        when(supplierMock.get()).thenReturn(10);

        // when
        Lazy.of(supplierMock).getIfComputed();

        // then
        verify(supplierMock, never()).get();
    }

    @Test
    public void testCallsToSupplierAfterBeingComputed() {
        // given
        @SuppressWarnings("unchecked")
        final Supplier<Integer> supplierMock = (Supplier<Integer>)mock(Supplier.class);
        when(supplierMock.get()).thenReturn(10);
        final Lazy<Integer> lazyInt = Lazy.of(supplierMock);

        // when
        lazyInt.get();
        lazyInt.get();
        lazyInt.getIfComputed();

        // then
        verify(supplierMock, times(1)).get();
    }
}
