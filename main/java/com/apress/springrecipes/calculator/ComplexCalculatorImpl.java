package com.apress.springrecipes.calculator;

/**
 * Date: 1/27/11
 * Time: 1:39 PM
 */
public class ComplexCalculatorImpl implements ComplexCalculator {


    @Override
    public Complex add(Complex a, Complex b) {
        int real = a.getReal() + b.getReal();
        int imaginary = a.getImaginary() + b.getImaginary();
        Complex result = new Complex(real, imaginary);
        System.out.println(a + " + " + b + " = " + result);
        return result;
    }

    @Override
    public Complex sub(Complex a, Complex b) {
        int real = a.getReal() - b.getReal();
        int imaginary = a.getImaginary() - b.getImaginary();
        Complex result = new Complex(real, imaginary);
        System.out.println(a + " - " + b + " = " + result);
        return result;
    }

}
