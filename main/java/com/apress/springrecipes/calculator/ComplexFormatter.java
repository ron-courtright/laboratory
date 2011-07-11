package com.apress.springrecipes.calculator;

import org.aspectj.lang.annotation.Aspect;

/**
 * Date: 1/27/11
 * Time: 2:55 PM
 */
public class ComplexFormatter {

    private String pattern;

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String format(Complex complex) {
        return pattern.replaceAll("a", Integer.toString(complex.getReal()))
                .replaceAll("b", Integer.toString(complex.getImaginary()));
    }

}
