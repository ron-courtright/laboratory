package com.apress.springrecipes.shop;

/**
 * Date: 1/20/11
 * Time: 5:57 PM
 */
public abstract class Product {

    // static initialization example
    public static final Product AA    = new Battery("AA", 2.5);
    public static final Product CDRO  = new Disc("CD-RO", 1.5);

    private String name;
    private double price;

    public Product() {}

    protected Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return name + " " + price;
    }
}
