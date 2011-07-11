package com.apress.springrecipes.calculator;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Date: 1/25/11
 * Time: 5:17 PM
 */
public class LoggingBeforeAdvice implements MethodBeforeAdvice {

    private static Logger logger = Logger.getLogger(LoggingBeforeAdvice.class);

    /**
     * Callback before a given method is invoked.
     *
     * @param method method being invoked
     * @param args   arguments to the method
     * @param target target of the method invocation. May be <code>null</code>.
     * @throws Throwable if this object wishes to abort the call.
     *                   Any exception thrown will be returned to the caller if it's
     *                   allowed by the method signature. Otherwise the exception
     *                   will be wrapped as a runtime exception.
     */
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        String preamble = target != null ? (target.getClass().getSimpleName() + ".") : "The method ";
        logger.info(preamble + method.getName() + "() begins with " + Arrays.toString(args));
    }
}
