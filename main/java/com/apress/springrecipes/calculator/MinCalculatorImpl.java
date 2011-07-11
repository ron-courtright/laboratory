package com.apress.springrecipes.calculator;

/**
 * Date: 1/27/11
 * Time: 11:46 AM
 */
public class MinCalculatorImpl implements MinCalculator {

    @Override
    public double min(double a, double b) {
        double result = (a >= b) ? b : a;
        System.out.println("max("+a+","+b+") = " + result);
        return result;
    }

}
