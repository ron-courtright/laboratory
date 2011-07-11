package com.apress.springrecipes.shop;

import java.util.Map;

/**
 * Date: 1/20/11
 * Time: 6:20 PM
 */
public class ProductCreator {

    private ProductCreator productCreator = null;

    private ProductCreator() {}

    private Map<String,Product> products;

    public void setProducts(Map<String,Product> products) {
        this.products = products;
    }

    public Product createProduct(String productId) {
        Product product = products.get(productId);
        if (product == null) {
            throw new IllegalArgumentException("Unknown product");
        }
        return product;
    }

}
