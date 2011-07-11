package com.apress.springrecipes.sequence;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

/**
 * Date: 1/20/11
 * Time: 2:07 PM
 */
public class CollectionInjectionBean {

    private Set<String> products;
    private Map<String,Integer> prices;

    public void setPrices(Map<String,Integer> prices) {
        this.prices = prices;
    }

    public void setProducts(Set<String> products) {
        this.products = products;
    }

    public String toString() {
        StringBuilder buffy = new StringBuilder("\n");
        for (String product : products) {
            int price = prices.get(product);
            buffy.append(product).append(" costs: $").append(price).append("\n");
        }
        return buffy.toString();
    }
}
