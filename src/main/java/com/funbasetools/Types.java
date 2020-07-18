package com.funbasetools;

import com.funbasetools.collections.Streams;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;

public final class Types {

    public static boolean anyNull(final Object...objects) {
        for (final Object obj: objects) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean allNonNull(final Object...objects) {
        for (final Object obj: objects) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    public static <T> T as(final Class<T> type, final Object obj) {
        if (is(type, obj)) {
            return type.cast(obj);
        }
        return null;
    }

    public static <T> T[] asArrayOf(final Class<T> type, final boolean allOrNone, final Object obj) {
        if (!isArray(obj)) {
            return null;
        }

        final Object[] arrayObj = (Object[])obj;
        final List<T> list = Streams
            .of(arrayObj)
            .map(o -> as(type, o))
            .take(arrayObj.length);

        if (allOrNone && list.size() < arrayObj.length) {
            return null;
        }

        @SuppressWarnings("unchecked")
        final T[] array = (T[]) Array.newInstance(type, list.size());
        return list.toArray(array);
    }

    public static <K, V> Map<K, V> asMapOf(final Class<K> keyType,
                                           final Class<V> valueType,
                                           final boolean allOrNone,
                                           final Object obj) {
        if (anyNull(keyType, valueType, obj) || !is(Map.class, obj)) {
            return null;
        }

        final Map<?, ?> mapObj = (Map<?, ?>) obj;
        final Map<K, V> result = mapObj
            .entrySet()
            .stream()
            .filter(entry -> is(keyType, entry.getKey()) && is(valueType, entry.getValue()))
            .map(entry -> Pair.of(as(keyType, entry.getKey()), as(valueType, entry.getValue())))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (allOrNone && result.size() < mapObj.size()) {
            return null;
        }

        return result;
    }

    public static <T> boolean is(final Class<T> type, Object obj) {
        return allNonNull(type, obj) && type.isAssignableFrom(obj.getClass());
    }

    public static boolean isArray(final Object obj) {
        return obj != null && obj.getClass().isArray();
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
