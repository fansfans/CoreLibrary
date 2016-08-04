package com.core.common.utils;

import java.math.BigDecimal;

/**
 * Created by admin on 16/4/27.
 */
public class CheckEqualUtil {

    private final static float EPSILON = 0.00000001F;

    public static boolean compareFloats(float f1, float f2) {
        return Math.abs(f1 - f2) <= EPSILON;
    }

    public static boolean compareDoubles(double d1, double d2) {
        BigDecimal bd1 = new BigDecimal(d1);
        BigDecimal bd2 = new BigDecimal(d2);

        return bd1.compareTo(bd2) == 0 ? true: false;
    }
}
