package com.apress.springrecipes.sequence;

/**
 * Date: 1/19/11
 * Time: 6:19 PM
 */
public class ReverseGenerator {
    private int initial;

    public void setInitial(int initial) {
        this.initial = initial;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return String.valueOf(initial);
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || !(getClass().equals(object.getClass())))
            return false;
        // so far so good
        ReverseGenerator generator = (ReverseGenerator)object;
        if (generator.initial != this.initial)
            return false;
        return true;
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return initial * 31;
    }

}
