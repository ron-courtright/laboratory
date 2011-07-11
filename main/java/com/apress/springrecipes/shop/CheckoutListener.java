package com.apress.springrecipes.shop;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.Date;

/**
 * Date: 1/24/11
 * Time: 4:07 PM
 */
public class CheckoutListener implements ApplicationListener {

    private static Logger logger;
    static {
        logger = Logger.getLogger(CheckoutListener.class);
    }

    private String formatMessage(double amount, Date time) {
        String message = "Checkout event [$" + amount + ", " + time + "]";
        return message;
    }

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof CheckoutEvent) {
            final CheckoutEvent checkoutEvent = (CheckoutEvent)event;
            double amount = checkoutEvent.getAmount();
            Date time = checkoutEvent.getTime();
            String message = formatMessage(amount, time);
            logger.info(message);
        }
    }
}
