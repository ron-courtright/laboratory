package com.apress.springrecipes.calculator;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Date: 1/26/11
 * Time: 9:12 PM
 */
public class AspectJMain {

    private static Logger logger = Logger.getLogger(AspectJMain.class);

    private static String beanXML = "com/apress/springrecipes/calculator/calculator-aop-beans.xml";

    private static void doArithmetic(ArithmeticCalculator arithmeticCalculator) throws Throwable {
        arithmeticCalculator.add(1.d, 2.d);
        arithmeticCalculator.sub(4.d, 3.d);
        arithmeticCalculator.mul(2.d, 3.d);
        arithmeticCalculator.div(4.d, 2.d);
        try {
            arithmeticCalculator.div(1.d, 0.d); // divide by zero
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage());
        }
        try {
            arithmeticCalculator.mul(1.d, 0.d); // multiply by zero
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage());
        }
        // introductions are now in order
        MaxCalculator maxCalculator = (MaxCalculator)arithmeticCalculator;
        maxCalculator.max(1.d,2.d);
        MinCalculator minCalculator = (MinCalculator)arithmeticCalculator;
        minCalculator.min(1.d,2.d);
        Counter counter = (Counter)arithmeticCalculator;
        counter.getCount();
    }

    private static void doConversion(UnitCalculator unitCalculator) throws Throwable {
        unitCalculator.kilogramToPound(10.0d);
        unitCalculator.kilometerToMile(40.d);
        try {
            unitCalculator.kilogramToPound(0.d);
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage());
        }
        Counter counter = (Counter)unitCalculator;
        counter.getCount();
    }

    private static void doComplex(ComplexCalculator complexCalculator) throws Throwable {
        Complex add = new Complex(1,2);
        Complex common = new Complex(2,3);
        complexCalculator.add(add, common);
        Complex sub = new Complex(5,8);
        complexCalculator.sub(sub, common);
        Counter counter = (Counter)complexCalculator;
        counter.getCount();
    }

    public static void main(String...args) {
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext(beanXML);
            ArithmeticCalculator arithmeticCalculator =
                    (ArithmeticCalculator)context.getBean("aop-arithmeticCalculator");
            doArithmetic(arithmeticCalculator);
            UnitCalculator unitCalculator =
                    (UnitCalculator)context.getBean("aop-unitCalculator");
            doConversion(unitCalculator);
            ComplexCalculator complexCalculator =
                    (ComplexCalculator)context.getBean("complexCalculator");
            doComplex(complexCalculator);
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
    }

}
