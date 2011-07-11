package com.apress.springrecipes.calculator;

import org.aopalliance.intercept.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Date: 1/25/11
 * Time: 4:24 PM
 */
public class CalculatorValidationHandler implements InvocationHandler {

    public static Object createProxy(Object target) {
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new CalculatorValidationHandler(target)
        );
        return proxy;
    }

    private Object target;

    public CalculatorValidationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        for (Object object : objects) {
            validate((Double)object);
        }
        Object result = method.invoke(target, objects);
        return result;
    }

    private void validate(double d) {
        if (d < 0) {
            throw new IllegalArgumentException("Validation error -> positive numbers only.");
        }
    }

}
