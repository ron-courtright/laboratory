package com.apress.springrecipes.calculator;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * Date: 1/25/11
 * Time: 5:32 PM
 */
public class LoggingAfterAdvice implements AfterReturningAdvice {

    private static Logger logger = Logger.getLogger(LoggingAfterAdvice.class);

    /**
     * Callback after a given method successfully returned.
     *
     * @param returnValue the value returned by the method, if any
     * @param method      method being invoked
     * @param args        arguments to the method
     * @param target      target of the method invocation. May be <code>null</code>.
     * @throws Throwable if this object wishes to abort the call.
     *                   Any exception thrown will be returned to the caller if it's
     *                   allowed by the method signature. Otherwise the exception
     *                   will be wrapped as a runtime exception.
     */
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        String preamble = target != null ? (target.getClass().getSimpleName() + ".") : "The method ";
        logger.info(preamble + method.getName() + "() ends with " + returnValue);
    }

}
