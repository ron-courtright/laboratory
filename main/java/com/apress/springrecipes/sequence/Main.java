package com.apress.springrecipes.sequence;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Date: 1/19/11
 * Time: 12:16 PM
 */
public class Main {

    private static Logger logger;
    static {
        logger = Logger.getLogger(Main.class);
    }

    private static String[] beanNames = {
            "sequenceGenerator-setBeans",       // uses bean property setters
            "sequenceGenerator-constructor",    // relies on a ctor
            "sequenceGenerator",                // child of baseSequenceGenerator
            "sequenceGenerator1",               // child of baseSequenceGenerator
            "sequenceGenerator2",               // child of baseSequenceGenerator
            "reverseGenerator"                  // child of baseGenerator
    };

    public static void main(String...args) {
        String beansXml = "com/apress/springrecipes/sequence/sequence-beans.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(beansXml);
        //
        StringBuilder buffy = new StringBuilder("\n");
        //
        for (String beanName : beanNames) {
            Object bean = context.getBean(beanName);
            buffy.append(beanName).append("\n");
            buffy.append(bean.toString()).append("\n");
            buffy.append(bean.toString()).append("\n");
            buffy.append("\n");
        }
        SequenceService sequenceService = (SequenceService) context.getBean("sequenceService");
        buffy.append("sequenceService").append("\n");
        for (int i = 0; i < 11; i++) {
            buffy.append(sequenceService.generate("IT"));
            buffy.append("\n");
        }
        logger.info(buffy);
        buffy.delete(0, buffy.length());
    }

}
