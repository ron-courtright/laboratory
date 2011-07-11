package com.apress.springrecipes.calculator;

/**
 * Date: 1/27/11
 * Time: 11:49 AM
 */
public class MaxCalculatorImpl implements MaxCalculator {

    @Override
    public double max(double a, double b) {
        double result = (a>=b) ? a : b;
        System.out.println("max("+a+","+b+") = " + result);
        return result;
    }

}
