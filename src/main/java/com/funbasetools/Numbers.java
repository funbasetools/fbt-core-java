package com.funbasetools;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class Numbers {

    public static <N extends Number> int compare(final N a, final N b) {
        if (a instanceof Integer) {
            return Integer.compare(a.intValue(), b.intValue());
        }
        if (a instanceof Long) {
            return Long.compare(a.longValue(), b.longValue());
        }
        if (a instanceof BigInteger) {
            return ((BigInteger) a).compareTo((BigInteger) b);
        }
        if (a instanceof BigDecimal) {
            return ((BigDecimal) a).compareTo((BigDecimal) b);
        }

        return Double.compare(a.doubleValue(), b.doubleValue());
    }

    @SuppressWarnings("unchecked")
    public static <N extends Number> N add(N a, N b) {
        if (a instanceof Integer) {
            return (N) (Integer) (a.intValue() + b.intValue());
        }
        if (a instanceof Long) {
            return (N) (Long) (a.longValue() + b.longValue());
        }
        if (a instanceof Double) {
            return (N) (Double) (a.doubleValue() + b.doubleValue());
        }
        if (a instanceof Float) {
            return (N) (Float) (a.floatValue() + b.floatValue());
        }
        if (a instanceof BigInteger) {
            return (N) ((BigInteger)a).add((BigInteger)b);
        }
        if (a instanceof BigDecimal) {
            return (N) ((BigDecimal)a).add((BigDecimal) b);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static <N extends Number> N subtract(N a, N b) {
        if (a instanceof Integer) {
            return (N) (Integer) (a.intValue() - b.intValue());
        }
        if (a instanceof Long) {
            return (N) (Long) (a.longValue() - b.longValue());
        }
        if (a instanceof Double) {
            return (N) (Double) (a.doubleValue() - b.doubleValue());
        }
        if (a instanceof Float) {
            return (N) (Float) (a.floatValue() - b.floatValue());
        }
        if (a instanceof BigInteger) {
            return (N) ((BigInteger)a).subtract((BigInteger)b);
        }
        if (a instanceof BigDecimal) {
            return (N) ((BigDecimal)a).subtract((BigDecimal) b);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static <N extends Number> N multiply(N a, N b) {
        if (a instanceof Integer) {
            return (N) (Integer) (a.intValue() * b.intValue());
        }
        if (a instanceof Long) {
            return (N) (Long) (a.longValue() * b.longValue());
        }
        if (a instanceof Double) {
            return (N) (Double) (a.doubleValue() * b.doubleValue());
        }
        if (a instanceof Float) {
            return (N) (Float) (a.floatValue() * b.floatValue());
        }
        if (a instanceof BigInteger) {
            return (N) ((BigInteger)a).multiply((BigInteger)b);
        }
        if (a instanceof BigDecimal) {
            return (N) ((BigDecimal)a).multiply((BigDecimal) b);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static <N extends Number> N divide(N a, N b) {
        if (a instanceof Integer) {
            return (N) (Integer) (a.intValue() / b.intValue());
        }
        if (a instanceof Long) {
            return (N) (Long) (a.longValue() / b.longValue());
        }
        if (a instanceof Double) {
            return (N) (Double) (a.doubleValue() / b.doubleValue());
        }
        if (a instanceof Float) {
            return (N) (Float) (a.floatValue() / b.floatValue());
        }
        if (a instanceof BigInteger) {
            return (N) ((BigInteger)a).divide((BigInteger)b);
        }
        if (a instanceof BigDecimal) {
            return (N) ((BigDecimal)a).divide((BigDecimal) b);
        }

        return null;
    }
}
