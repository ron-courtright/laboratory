package com.apress.springrecipes.calculator;

/**
 * Date: 1/25/11
 * Time: 2:27 PM
 */
public class UnitCalculatorImpl implements UnitCalculator {

    protected static double K2P = 2.2d;
    protected static double K2M = 0.65d;

    @Override
    public double kilogramToPound(double kilogram) {
        double pound = kilogram * K2P;
        System.out.println(kilogram + " * " + K2P + " = " + pound);
        return pound;
    }

    @Override
    public double kilometerToMile(double kilometer) {
        double mile = kilometer * K2M;
        System.out.println(kilometer + " * " + K2M + " = " + mile);
        return mile;
    }
}
