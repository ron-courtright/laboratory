package com.apress.springrecipes.shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 1/24/11
 * Time: 10:39 AM
 */
public class ShoppingCart {

    private List<Product> items = new ArrayList<Product>();

    public void addItem(Product item) {
        items.add(item);
    }

    public List<Product> getItems() {
        return items;
    }

    @Override
    public String toString() {
        String result = "cart contains:\t" + getItems();
        return result;
    }
}
