package com.apress.springrecipes.calculator;

/**
 * Date: 1/27/11
 * Time: 1:13 PM
 */
public class CounterImpl implements Counter {

    private int count;

    @Override
    public void increase() {
        count++;
    }

    @Override
    public int getCount() {
        return count;
    }
}
