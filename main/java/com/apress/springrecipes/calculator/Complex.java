package com.apress.springrecipes.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Date: 1/27/11
 * Time: 1:33 PM
 */
@Configurable("complex")
public class Complex {

    private int real;
    private int imaginary;
    private ComplexFormatter formatter;

    public Complex(int real, int imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public int getReal() {
        return real;
    }

    public void setReal(int real) {
        this.real = real;
    }

    public int getImaginary() {
        return imaginary;
    }

    public void setImaginary(int imaginary) {
        this.imaginary = imaginary;
    }

    @Autowired
    public void setFormatter(@Qualifier("complexFormatter") ComplexFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Complex complex = (Complex) o;

        if (imaginary != complex.imaginary) return false;
        if (real != complex.real) return false;
        if (formatter != null ? !formatter.equals(complex.formatter) : complex.formatter != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = real;
        result = 31 * result + imaginary;
        result = 31 * result + (formatter != null ? formatter.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        /*
        return "(" + real + " + " + imaginary + "i)";
        */
        // above call overtaken by the following aspectj configured operation:
        String result = formatter.format(this);
        return result;
        
    }

}
