package com.apress.springrecipes.shop;

import java.util.Date;

/**
 * Date: 1/24/11
 * Time: 9:38 AM
 */
public class ProductRanking {

    private Product bestSeller;
    private Date fromDate;
    private Date toDate;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Product getBestSeller() {
        return bestSeller;
    }

    public void setBestSeller(Product bestSeller) {
        this.bestSeller = bestSeller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductRanking that = (ProductRanking) o;

        if (bestSeller != null ? !bestSeller.equals(that.bestSeller) : that.bestSeller != null) return false;
        if (fromDate != null ? !fromDate.equals(that.fromDate) : that.fromDate != null) return false;
        if (toDate != null ? !toDate.equals(that.toDate) : that.toDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 31;
        hash = hash + (bestSeller != null ? bestSeller.hashCode() : 0);
        hash = hash + (fromDate != null ? fromDate.hashCode() : 0);
        hash = hash + (toDate != null ? toDate.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buffy = new StringBuilder();
        buffy.append(bestSeller.toString());
        buffy.append(", best seller from ");
        buffy.append(fromDate).append(" to ").append(toDate);
        return buffy.toString();
    }

}
