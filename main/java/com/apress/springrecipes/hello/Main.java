package com.apress.springrecipes.hello;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Date: 1/18/11
 * Time: 10:28 PM
 */
public class Main {

    public static void main(String...args) {
        String beansXml = "com/apress/springrecipes/hello/beans.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(beansXml);
        HelloWorld helloWorld = (HelloWorld)context.getBean("helloWorld");
        helloWorld.hello();
    }

}
