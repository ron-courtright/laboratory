package com.apress.springrecipes.calculator;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Date: 1/27/11
 * Time: 11:01 AM
 */
@Aspect
public class CalculatorPointcuts {

    @Pointcut("execution(* com.apress.springrecipes.calculator.*Calculator.*(..))")
    public void logging() {}

    @Pointcut("execution(* com.apress.springrecipes.calculator.*Calculator.*(double,..))")
    public void validation() {}

}
