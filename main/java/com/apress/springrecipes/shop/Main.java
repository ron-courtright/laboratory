package com.apress.springrecipes.shop;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Date: 1/20/11
 * Time: 6:08 PM
 */
public class Main {

    private static Logger logger;
    private static String[] beanNames;
    private static String beanXml = "com/apress/springrecipes/shop/shop-beans.xml";
    static {
        logger = Logger.getLogger(Main.class);
        beanNames = new String[] {
            "aaa",
            "cdrw",
            "dvdrw"
        };
    }

    public static void main(String...args) {
        StringBuilder buffy = new StringBuilder("\n");
        ApplicationContext context =
                new ClassPathXmlApplicationContext(beanXml);
        // iterate through the bean names and print individual values
        try {
            buffy.append("beanName iteration example\n");
            for (String beanName : beanNames) {
                Product product = (Product)context.getBean(beanName);
                buffy.append(product).append("\n");
            }
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
        // Use AbstractFactoryBean implementations.
        try {
            buffy.append("Use AbstractFactoryBean implementations.\n");
            DiscountFactoryBean aaa = new DiscountFactoryBean();
            aaa.setProduct(new Battery("AAA", 2.5));
            aaa.setDiscount(0.2d);
            Product battery = (Product)aaa.createInstance();
            buffy.append(battery).append("\n");
            DiscountFactoryBean cdrw = new DiscountFactoryBean();
            cdrw.setProduct(new Disc("CD-RW", 1.5));
            cdrw.setDiscount(0.1d);
            Product disc = (Product)cdrw.createInstance();
            buffy.append(disc).append("\n");
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
        // static init example
        try {
            buffy.append("static init example\n");
            Product aa = Product.AA;
            buffy.append(aa).append("\n");
            Product cdro = Product.CDRO;
            buffy.append(cdro).append("\n");
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
        // property path
        try {
            buffy.append("Property path example\n");
            Product bestSeller = (Product) context.getBean("bestSeller");
            buffy.append(bestSeller).append("\n");
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
        // shopping cart example
        ShoppingCart cart1 = null;
        try {
            buffy.append("Shopping cart & bean prototype (vs. singleton) scoping example\n");
            Product aaa = (Product)context.getBean("aaa");
            Product cdrw = (Product)context.getBean("cdrw");
            Product dvdrw = (Product)context.getBean("dvdrw");
            cart1 = (ShoppingCart)context.getBean("shoppingCart");
            cart1.addItem(aaa);
            cart1.addItem(cdrw);
            buffy.append("#1 ").append(cart1).append("\n");
            ShoppingCart cart2 = (ShoppingCart)context.getBean("shoppingCart");
            cart2.addItem(dvdrw);
            buffy.append("#2 ").append(cart2).append("\n");
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
        // cashier example:  init & destroy
        try {
            Cashier cashier1 = (Cashier)context.getBean("cashier1");
            cashier1.checkout(cart1);
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
        // product ranking
        try {
            buffy.append("Registering a built-in property editor example\n");
            ProductRanking productRanking = (ProductRanking)context.getBean("productRanking");
            buffy.append("Product ranking for ").append(productRanking.getBestSeller());
            buffy.append(" from ").append(productRanking.getFromDate());
            buffy.append(" to ").append(productRanking.getToDate()).append("\n");
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
        }
        // print log messages
        buffy.delete(buffy.length()-1, buffy.length());
        logger.info(buffy);
        buffy.delete(0, buffy.length());
    }
}
