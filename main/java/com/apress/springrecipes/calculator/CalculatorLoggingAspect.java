package com.apress.springrecipes.calculator;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

/**
 * Date: 1/26/11
 * Time: 9:08 PM
 */
@Aspect
public class CalculatorLoggingAspect implements Ordered {

    private Logger logger = Logger.getLogger(CalculatorLoggingAspect.class);

    private int order;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /* made public with move to CalculatorPointcuts
    @Pointcut("execution(* *.*(..))")
    private void logging() {}
    */

    private StringBuilder trimBrackets(String target) {
        StringBuilder buffy = new StringBuilder(target);
        buffy.delete(0,1);
        buffy.delete(buffy.length()-1,buffy.length());
        return buffy;
    }

    @Before("CalculatorPointcuts.logging()")
    public void logBefore(JoinPoint joinPoint) throws Throwable {
        StringBuilder buffy = new StringBuilder();
        buffy.append(joinPoint.getTarget().getClass().getSimpleName()).append(".");
        buffy.append(joinPoint.getSignature().getName()).append("(");
        buffy.append(trimBrackets(Arrays.toString(joinPoint.getArgs())));
        buffy.append(")");
        logger.info(buffy);
        buffy.delete(0, buffy.length());
    }

    @After("CalculatorPointcuts.logging()")
    public void logAfter(JoinPoint joinPoint) throws Throwable {
        StringBuilder buffy = new StringBuilder();
        buffy.append(joinPoint.getTarget().getClass().getSimpleName()).append(".");
        buffy.append(joinPoint.getSignature().getName());
        buffy.append("() has completed");
        logger.info(buffy);
        buffy.delete(0, buffy.length());
    }
    
    @AfterReturning(
            pointcut  = "CalculatorPointcuts.logging()",
            returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        StringBuilder buffy = new StringBuilder();
        buffy.append(joinPoint.getTarget().getClass().getSimpleName()).append(".");
        buffy.append(joinPoint.getSignature().getName());
        buffy.append("() returns: ");
        buffy.append(result);
        logger.info(buffy);
        buffy.delete(0, buffy.length());
    }

    @AfterThrowing(
            pointcut = "CalculatorPointcuts.logging()",
            throwing = "t")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable t) {
        StringBuilder buffy = new StringBuilder();
        buffy.append(t);
        buffy.append(" has been thrown by ");
        buffy.append(joinPoint.getSignature().getName());
        buffy.append("()");
        logger.error(buffy);
        buffy.delete(0, buffy.length());
    }

    /* A wonderful example of Around, although it is a bit too powerful... and noisy given all the other routines in place.
    @Around("CalculatorPointcuts.logging()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // before
        StringBuilder buffy = new StringBuilder();
        buffy.append("\nOyez, oyez...  All rise and understand that these are the particulars of this method call:\n");
        buffy.append("declaringClassName=\t").append(joinPoint.getThis().getClass().getName()).append("\n");
        buffy.append("kind=\t\t\t\t").append(joinPoint.getKind()).append("\n");
        buffy.append("declaringTypeName=\t").append(joinPoint.getSignature().getDeclaringTypeName()).append("\n");
        buffy.append("name=\t\t\t\t").append(joinPoint.getSignature().getName()).append("\n");
        buffy.append("args=\t\t\t\t").append(Arrays.toString(joinPoint.getArgs())).append("\n");
        buffy.append("targetClassName=\t").append(joinPoint.getTarget().getClass().getName()).append("\n");
        logger.info(buffy);
        buffy.delete(0, buffy.length());
        try {
            Object result = joinPoint.proceed();
            // after returning
            buffy.append("The method ");
            buffy.append(joinPoint.getSignature().getName());
            buffy.append("() ends right here, right now");
            logger.info(buffy);
            buffy.delete(0, buffy.length());
            return result;
        } catch (Throwable t) { // after throwing
            buffy.append(t);
            buffy.append(" has been thrown by ");
            buffy.append(joinPoint.getSignature().getName());
            buffy.append("()");
            logger.error(buffy);
            buffy.delete(0, buffy.length());
            throw t;
        }
    }
    */

}
