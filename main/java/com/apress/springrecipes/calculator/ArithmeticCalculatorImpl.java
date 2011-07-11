package com.apress.springrecipes.calculator;

/**
 * Date: 1/25/11
 * Time: 2:22 PM
 */
public class ArithmeticCalculatorImpl implements ArithmeticCalculator {

    @LoggingRequired
    @Override
    public double add(double a, double b) {
        double result = a + b;
        System.out.println(a + " + " + b + " = " + result);
        return result;
    }

    @LoggingRequired
    @Override
    public double sub(double a, double b) {
        double result = a - b;
        System.out.println(a + " - " + b + " = " + result);
        return result;
    }

    @LoggingRequired
    @Override
    public double mul(double a, double b) {
        double result = a * b;
        System.out.println(a + " * " + b + " = " + result);
        return result;
    }

    @LoggingRequired
    @ValidationRequired
    @Override
    public double div(double a, double b) {
        double result = a / b;
        System.out.println(a + " / " + b + " = " + result);
        return result;
    }

}
