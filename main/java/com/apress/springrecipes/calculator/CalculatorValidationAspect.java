package com.apress.springrecipes.calculator;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Date: 1/27/11
 * Time: 10:02 AM
 */
@Aspect
public class CalculatorValidationAspect implements Ordered {

    private Logger logger = Logger.getLogger(CalculatorValidationAspect.class);
    private int order;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Before("CalculatorPointcuts.validation()")
    public void validateBefore(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            validate((Double)arg, joinPoint);
        }
    }

    private void validate(double d, JoinPoint joinPoint) {
        if (d <= 0) {
            StringBuilder buffy = new StringBuilder();
            buffy.append(joinPoint.getSignature().getDeclaringTypeName())
                    .append(".")
                    .append(joinPoint.getSignature().getName())
                    .append("(")
                    .append(joinPoint.getArgs()[0])
                    .append(",")
                    .append(joinPoint.getArgs()[1])
                    .append(") throws IllegalArgumentException");
            logger.debug(buffy);
            buffy.delete(0, buffy.length());
            throw new IllegalArgumentException("Only positive operands need apply");
        }
    }

}
