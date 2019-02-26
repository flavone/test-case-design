package com.cfpamf.test.design.util;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author flavone
 * @date 2019/02/25
 */
public class BigDecimalUtil {
    private static final String SMALLEST_VALUE = "0.0000000000000000000001";

    /**
     * 开平方
     *
     * @param num
     * @return
     */
    public static BigDecimal sqrt(BigDecimal num) {
        if (num.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal x = num.divide(new BigDecimal("2"), MathContext.DECIMAL128);
        while (x.subtract(x = sqrtIteration(x, num)).abs().compareTo(new BigDecimal(SMALLEST_VALUE)) > 0) {
        }
        return x;
    }

    private static BigDecimal sqrtIteration(BigDecimal x, BigDecimal n) {
        return x.add(n.divide(x, MathContext.DECIMAL128)).divide(new BigDecimal("2"), MathContext.DECIMAL128);
    }
}
