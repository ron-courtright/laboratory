package com.apress.springrecipes.calculator;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Date: 1/25/11
 * Time: 2:37 PM
 */
public class Main {
    private static Logger logger = Logger.getLogger(Main.class);
    private static String beanXml = "com/apress/springrecipes/calculator/calculator-beans.xml";

    private static ApplicationContext getContext() {
        ApplicationContext context = new ClassPathXmlApplicationContext(beanXml);
        return context;
    }

    private static void doArithmeticCalculation(ArithmeticCalculator arithmeticCalculator) throws Throwable {
        arithmeticCalculator.add(1.d,2.d);
        arithmeticCalculator.sub(2.d,1.d);
        arithmeticCalculator.mul(2.d,4.d);
        arithmeticCalculator.div(4.d,2.d);
        // test validation handler
        try {
            arithmeticCalculator.div(1,0);
        } catch (Throwable ignored) {
        }
    }

    private static void doUnitCalculation(UnitCalculator unitCalculator) throws Throwable {
        unitCalculator.kilogramToPound(1.d);
        unitCalculator.kilometerToMile(40.d);
        // test validation handler
        try {
            unitCalculator.kilogramToPound(-1.d);
        } catch (Throwable ignored) {}
    }


    private static void useProxyInvocation() throws Throwable {
        logger.info("use JDK proxy invocation handler");
        ArithmeticCalculator arithmeticCalculatorImpl = new ArithmeticCalculatorImpl();
        // use invocation handler for logging
        Object arithmeticProxy =
                CalculatorLoggingHandler.createProxy(arithmeticCalculatorImpl);
        ArithmeticCalculator arithmeticCalculator =
                (ArithmeticCalculator) CalculatorValidationHandler.createProxy(arithmeticProxy);
        doArithmeticCalculation(arithmeticCalculator);
        // unit calculator
        UnitCalculator unitCalculatorImpl = new UnitCalculatorImpl();
        Object unitProxy =
                CalculatorLoggingHandler.createProxy(unitCalculatorImpl);
        UnitCalculator unitCalculator =
                (UnitCalculator) CalculatorValidationHandler.createProxy(unitProxy);
        doUnitCalculation(unitCalculator);
    }

    public static void useSpring() throws Throwable {
        logger.info("use Spring");
        ApplicationContext context = getContext();
        //
        ArithmeticCalculator arithmeticCalculator =
                (ArithmeticCalculator)context.getBean("arithmeticCalculatorProxy");
        doArithmeticCalculation(arithmeticCalculator);
        //
        UnitCalculator unitCalculator =
                (UnitCalculator)context.getBean("unitCalculatorProxy");
        doUnitCalculation(unitCalculator);
    }

    public static void useLoggingAroundAdvice() throws Throwable {
        logger.info("use LoggingAroundAdvice");
        ApplicationContext context = getContext();
        //
        ArithmeticCalculator arithmeticCalculator =
                (ArithmeticCalculator)context.getBean("wrappedArithmeticCalculator");
        doArithmeticCalculation(arithmeticCalculator);
        //
        UnitCalculator unitCalculator =
                (UnitCalculator)context.getBean("wrappedUnitCalculator");
        doUnitCalculation(unitCalculator);
    }

    public static void useMethodNamePointcut() throws Throwable {
        logger.info("use methodNamePointcut");
        ApplicationContext context = getContext();
        ArithmeticCalculator addSubArithmeticCalculator =
                (ArithmeticCalculator)context.getBean("addSubPointcutArithmeticCalculator");
        addSubArithmeticCalculator.add(1.d, 2.d);
        addSubArithmeticCalculator.sub(3.5d,1.25d);
        ArithmeticCalculator genericCalculator =
                (ArithmeticCalculator)context.getBean("genericCalculator");
        genericCalculator.add(1.d, 1.d);
        genericCalculator.sub(2.d, 2.d);
        genericCalculator.mul(2.d, 3.d);
        genericCalculator.div(6.d, 3.d);
    }

    public static void useAspectJ() throws Throwable {
        logger.info("use AspectJ");
        ApplicationContext context = getContext();
        UnitCalculator unitCalculator = (UnitCalculator)context.getBean("aspectjCalculator");
        doUnitCalculation(unitCalculator);
    }

    public static void useAutoproxy() throws Throwable {
        logger.info("use Autoproxy");
        ApplicationContext context = getContext();
        ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator)context.getBean("arithmeticCalculator");
        doArithmeticCalculation(arithmeticCalculator);
        UnitCalculator unitCalculator = (UnitCalculator)context.getBean("unitCalculator");
        doUnitCalculation(unitCalculator);
    }

    public static void main(String...args) {
        try {
            useProxyInvocation();
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
        try {
            useSpring();
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
        try {
            useLoggingAroundAdvice();
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
        try {
            useMethodNamePointcut();
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
        try {
            useAspectJ();
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
        try {
            useAutoproxy();
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
    }
}
