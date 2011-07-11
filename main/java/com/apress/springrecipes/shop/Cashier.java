package com.apress.springrecipes.shop;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.*;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Date: 1/24/11
 * Time: 11:12 AM
 */
public class Cashier implements BeanNameAware,MessageSourceAware,ApplicationEventPublisherAware,StorageConfig {

    private static Logger logger;
    private static String fileExt;
    private static String alertProperty;
    static {
        logger = Logger.getLogger(Cashier.class);
        fileExt = ".txt";
        alertProperty = "alert.checkout";
    }

    private String name;
    private String path;
    private Writer writer;
    private MessageSource messageSource;
    private ApplicationEventPublisher applicationEventPublisher;

    public void setPath(String path) {
        this.path = path;
    }

    @PostConstruct
    public void openFile() throws IOException {
        File logFile = new File(path, name + fileExt);
        FileOutputStream fos = new FileOutputStream(logFile, true);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        writer = new BufferedWriter(osw);
    }

    public void checkout(ShoppingCart cart) throws Throwable {
        double total = 0d;
        for (Product product : cart.getItems()) {
            total += product.getPrice();
        }
        long time = Calendar.getInstance().getTimeInMillis();
        Date date = new Date(time);
        String logEntry = date + "\t" + total + "\r\n";
        writer.write(logEntry);
        writer.flush();
        // log alerts
        Object[] params = {total, date};
        StringBuilder buffy = new StringBuilder();
        buffy.append(messageSource.getMessage(alertProperty, params, Locale.US ));
        logger.info(buffy);
        buffy.delete(0, buffy.length());
        // publish event
        CheckoutEvent checkoutEvent = new CheckoutEvent(this, total, date);
        applicationEventPublisher.publishEvent(checkoutEvent);
    }

    @PreDestroy
    public void closeFile() throws IOException {
        writer.close();
    }

    /**
     * Set the name of the bean in the bean factory that created this bean.
     * <p>Invoked after population of normal bean properties but before an
     * init callback such as {@link org.springframework.beans.factory.InitializingBean#afterPropertiesSet()}
     * or a custom init-method.
     *
     * This operation effectively overloads the name property.  Rather than have the XML set the name, use
     * the bean ID as the moniker for a given instance.  So this operation allows the implementer to remove
     * the name setter operation.
     *
     * @param name the name of the bean in the factory.
     *             Note that this name is the actual bean name used in the factory, which may
     *             differ from the originally specified name: in particular for inner bean
     *             names, the actual bean name might have been made unique through appending
     *             "#..." suffixes. Use the {@link org.springframework.beans.factory.BeanFactoryUtils#originalBeanName(String)}
     *             method to extract the original bean name (without suffix), if desired.
     */
    @Override
    public void setBeanName(String name) {
        this.name = name;
    }

    @Override
    public String getPath() {
        return path;
    }

    /**
     * Set the MessageSource that this object runs in.
     * <p>Invoked after population of normal bean properties but before an init
     * callback like InitializingBean's afterPropertiesSet or a custom init-method.
     * Invoked before ApplicationContextAware's setApplicationContext.
     *
     * @param messageSource message sourceto be used by this object
     */
    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Set the ApplicationEventPublisher that this object runs in.
     * <p>Invoked after population of normal bean properties but before an init
     * callback like InitializingBean's afterPropertiesSet or a custom init-method.
     * Invoked before ApplicationContextAware's setApplicationContext.
     *
     * @param applicationEventPublisher event publisher to be used by this object
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
