package com.apress.springrecipes.shop;

import com.apple.eawt.Application;
import org.springframework.context.ApplicationEvent;

import java.util.Date;

/**
 * Date: 1/24/11
 * Time: 3:58 PM
 */
public class CheckoutEvent extends ApplicationEvent {

    private double amount;
    private Date time;

    public CheckoutEvent(Object source, double amount, Date time) {
        super(source);
        this.amount = amount;
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTime() {
        return time;
    }

}
