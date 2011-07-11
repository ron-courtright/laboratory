package com.apress.springrecipes.shop;

import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * Date: 1/24/11
 * Time: 8:34 AM
 */
public class DiscountFactoryBean extends AbstractFactoryBean {

    private Product product;
    private double  discount;

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * This abstract method declaration mirrors the method in the FactoryBean
     * interface, for a consistent offering of abstract template methods.
     *
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    @Override
    public Class<?> getObjectType() {
        return product.getClass();
    }

    /**
     * Template method that subclasses must override to construct
     * the object returned by this factory.
     * <p>Invoked on initialization of this FactoryBean in case of
     * a singleton; else, on each {@link #getObject()} call.
     *
     * @return the object returned by this factory
     * @throws Exception if an exception occured during object creation
     * @see #getObject()
     */
    @Override
    protected Object createInstance() throws Exception {
        double price = product.getPrice() * (1 - discount);
        product.setPrice(price);
        return product;
    }
}
