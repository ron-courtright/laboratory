package com.apress.springrecipes.sequence;

/**
 * Date: 1/20/11
 * Time: 1:59 PM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CollectionInjectionBeanTest {

    private static Logger logger;
    private static String[] beanNames;
    static {
        logger = Logger.getLogger(CollectionInjectionBean.class);
        beanNames = new String[]{"cars", "operatingSystems"};
    }

    public static void main(String... ar) {
        try {
            ApplicationContext context =
                    new ClassPathXmlApplicationContext("com/apress/springrecipes/sequence/collections-beans.xml");
            for (String beanName : beanNames) {
                Object bean = context.getBean(beanName);
                logger.info(bean.toString());
            }
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
    }

}
