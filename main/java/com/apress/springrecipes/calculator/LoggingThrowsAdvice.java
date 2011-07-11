package com.apress.springrecipes.calculator;

import org.apache.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Date: 1/25/11
 * Time: 5:50 PM
 */
public class LoggingThrowsAdvice implements ThrowsAdvice {
    private Logger logger = Logger.getLogger(LoggingThrowsAdvice.class);

    public void afterThrowing(Method method,
                              Object[] args,
                              Object target,
                              IllegalArgumentException e) throws Throwable {
        StringBuilder message = new StringBuilder();
        message.append("Illegal argument ");
        message.append(Arrays.toString(args));
        message.append(" for ");
        if (target != null) {
            message.append(target.getClass().getSimpleName()).append(".");
        }
        message.append(method.getName()).append("()");
        logger.error(message);
        message.delete(0, message.length());
    }
}
