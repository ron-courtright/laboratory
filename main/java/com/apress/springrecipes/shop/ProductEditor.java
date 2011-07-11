package com.apress.springrecipes.shop;

import org.apache.log4j.Logger;

import java.beans.PropertyEditorSupport;

/**
 * Date: 1/24/11
 * Time: 4:54 PM
 */
public class ProductEditor extends PropertyEditorSupport {

    private static Logger logger;
    static {
        logger = Logger.getLogger(ProductEditor.class);
    }

    @Override
    public String getAsText() {
        Product product = (Product) getValue();
        StringBuilder buffy = new StringBuilder();
        buffy.append(product.getClass().getName()).append(";");
        buffy.append(product.getName()).append(",");
        buffy.append(product.getPrice());
        return buffy.toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String[] parts = text.split(",");
        try {
            String className = parts[0];
            Product product = (Product)Class.forName(className).newInstance();
            String productName = parts[1];
            product.setName(productName);
            double price = Double.parseDouble(parts[2]);
            product.setPrice(price);
            setValue(product);
        } catch (Throwable t) {
            logger.error(t.getLocalizedMessage(), t);
            throw new IllegalArgumentException(t);
        }
    }
}
