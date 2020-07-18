package com.funbasetools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.funbasetools.collections.Streams;
import org.junit.Test;

public class TypesTest {

    @Test
    public void testAsReturnsCastedType() {
        // given
        final Object obj = "sample";

        // when
        final String str = Types.as(String.class, obj);

        // then
        assertNotNull(str);
        assertSame(obj, str);
    }

    @Test
    public void testAsReturnsNull() {
        // given
        final Object obj = "sample";

        // when
        final Integer integer = Types.as(Integer.class, obj);

        // then
        assertNull(integer);
    }

    @Test
    public void testIsType() {
        // given
        final Object obj = "sample";

        // then
        assertTrue(Types.is(String.class, obj));
    }

    @Test
    public void testIsNotType() {
        // given
        final Object obj = "sample";

        // then
        assertFalse(Types.is(Integer.class, obj));
    }

    @Test
    public void testAsArrayOfReturnsCastedArray() {
        // given
        final Object obj = new String[] {
            "str1",
            "str2",
            "str3",
        };

        // when
        final String[] array = Types.asArrayOf(String.class, obj);

        // then
        assertNotNull(array);
        assertEquals(3, array.length);
        assertTrue(Streams.of(array).corresponds(Streams.of((Object[])obj)));
    }

    @Test
    public void testAsArrayOfReturnsNull() {
        // given
        final Object obj = new String[] {
            "str1",
            "str2",
            "str3",
        };

        // when
        final Integer[] array = Types.asArrayOf(Integer.class, obj);

        // then
        assertNull(array);
    }

    @Test
    public void testIsArrayOf() {
        // given
        final Object obj = new String[] {
            "str1",
            "str2",
            "str3",
        };

        // then
        assertTrue(Types.isArrayOf(String.class, obj));
    }

    @Test
    public void testIsNotArrayOf() {
        // given
        final Object obj = new String[] {
            "str1",
            "str2",
            "str3",
        };

        // then
        assertFalse(Types.isArrayOf(Integer.class, obj));
    }
}
