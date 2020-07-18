package com.funbasetools;

import com.funbasetools.collections.Streams;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;

public final class Types {

    public static boolean anyNull(final Object...objects) {
        return Streams
            .of(objects)
            .exist(Objects::isNull);
    }

    public static boolean allNonNull(final Object...objects) {
        return Streams
            .of(objects)
            .forAll(Objects::nonNull);
    }

    public static <T> T as(final Class<T> type, final Object obj) {
        if (is(type, obj)) {
            return type.cast(obj);
        }

        return null;
    }

    public static <T> T[] asArrayOf(final Class<T> type, final Object obj) {
        if (!isArrayOf(type, obj)) {
            return null;
        }

        final Object[] arrayObj = (Object[])obj;

        @SuppressWarnings("unchecked")
        final T[] array = (T[]) Array.newInstance(type, arrayObj.length);

        final List<T> list = Streams
            .of(arrayObj)
            .map(o -> as(type, o))
            .take(arrayObj.length);

        return list.toArray(array);
    }

    public static <T> boolean is(final Class<T> type, Object obj) {
        return allNonNull(type, obj) && type.isAssignableFrom(obj.getClass());
    }

    public static <T> boolean isArrayOf(final Class<T> type, final Object obj) {
        if (anyNull(type, obj)) {
            return false;
        }

        final Class<?> objClass = obj.getClass();
        return objClass.isArray() && type.isAssignableFrom(objClass.getComponentType());
    }

    private Types() {
    }
}
