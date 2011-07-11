package com.apress.springrecipes.calculator;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * Date: 1/25/11
 * Time: 9:39 PM
 */
public class LoggingAroundAdvice implements MethodInterceptor {

    private Logger logger = Logger.getLogger(LoggingAroundAdvice.class);

    /**
     * Wrap a method invocation with useful logging information.
     * @param methodInvocation
     * @return the result of the invocation
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object result = null;
        String name = methodInvocation.getMethod().getName();
        String args = Arrays.toString(methodInvocation.getArguments());
        StringBuilder buffy = new StringBuilder();
        buffy.append(name);
        buffy.append("() begins with ");
        buffy.append(args);
        logger.info(buffy);
        buffy.delete(0, buffy.length());
        try {
            result = methodInvocation.proceed();
            buffy.append(name);
            buffy.append("() ends with ");
            buffy.append(result);
            logger.info(buffy);
            buffy.delete(0,buffy.length());
        } catch (IllegalArgumentException iae) {
            buffy.append("Illegal argument: ");
            buffy.append(args);
            buffy.append(" for ");
            buffy.append(name);
            buffy.append("()");
            logger.error(buffy);
            buffy.delete(0, buffy.length());
            throw iae;
        } catch (Throwable t) {
            buffy.append(name).append("() failed to proceed: ");
            buffy.append(t.getLocalizedMessage());
            logger.error(buffy, t);
            buffy.delete(0, buffy.length());
            throw t;
        }
        return result;
    }

}
