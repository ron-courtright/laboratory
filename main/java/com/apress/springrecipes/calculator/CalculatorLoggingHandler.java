package com.apress.springrecipes.calculator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Date: 1/25/11
 * Time: 2:56 PM
 */
public class CalculatorLoggingHandler implements InvocationHandler {

    public static Object createProxy(Object target) {
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new CalculatorLoggingHandler(target)
        );
        return proxy;
    }

    private Log log = LogFactory.getLog(CalculatorLoggingHandler.class);
    private Object target;

    public CalculatorLoggingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        String preamble = target != null ? (target.getClass().getSimpleName() + ".") : "The ";
        // Log the method beginning with the method name and arguments.
        log.info(preamble
                + method.getName()
                + "() begins with "
                + Arrays.toString(objects));
        // Perform the actual calculation on the target calculator object by calling
        // Method.invoke() and passing in the target object and method arguments.
        Object result = method.invoke(target, objects);
        // Log the method ending with the returning result.
        log.info(preamble
                + method.getName()
                + "() ends with "
                + result);
        return result;
    }
}
