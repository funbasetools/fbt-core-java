package com.funbasetools;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public final class BytesUtil {

    public static byte[] toByteArray(short value) {
        return ByteBuffer.allocate(Short.BYTES).putShort(value).array();
    }

    public static byte[] toByteArray(int value) {
        return ByteBuffer.allocate(Integer.BYTES).putInt(value).array();
    }

    public static byte[] toByteArray(long value) {
        return ByteBuffer.allocate(Long.BYTES).putLong(value).array();
    }

    public static byte[] toByteArray(float value) {
        return ByteBuffer.allocate(Float.BYTES).putFloat(value).array();
    }

    public static byte[] toByteArray(double value) {
        return ByteBuffer.allocate(Double.BYTES).putDouble(value).array();
    }

    public static byte[] toByteArray(BigInteger value) {
        return value.toByteArray();
    }

    public static short asShort(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes, index, Short.BYTES).asShortBuffer().get();
    }

    public static short asShort(byte[] bytes) {
        return asShort(bytes, 0);
    }

    public static int asInteger(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes, index, Integer.BYTES).asIntBuffer().get();
    }

    public static int asInteger(byte[] bytes) {
        return asInteger(bytes, 0);
    }

    public static long asLong(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes, index, Long.BYTES).asLongBuffer().get();
    }

    public static long asLong(byte[] bytes) {
        return asLong(bytes, 0);
    }

    public static float asFloat(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes, index, Float.BYTES).asFloatBuffer().get();
    }

    public static float asFloat(byte[] bytes) {
        return asFloat(bytes, 0);
    }

    public static double asDouble(byte[] bytes, int index) {
        return ByteBuffer.wrap(bytes, index, Double.BYTES).asDoubleBuffer().get();
    }

    public static double asDouble(byte[] bytes) {
        return asDouble(bytes, 0);
    }

    public static BigInteger asBigInteger(byte[] bytes) {
        return new BigInteger(bytes);
    }

    private BytesUtil() {
    }
}
