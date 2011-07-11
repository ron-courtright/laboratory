package com.apress.springrecipes.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;

/**
 * Date: 1/19/11
 * Time: 11:33 AM
 */
public class SequenceGenerator {

    private int    initial;
    private int    counter;

    @Autowired
    @Generator("buffy")
    private PrefixGenerator prefixGenerator;

    private Set<Integer> suffixes;

    /**
     * Default ctor that does only that.
     */
    public SequenceGenerator() {}

    /**
     * Inject inititialized values as part of construction.
     * @param initial The version.
     */
    public SequenceGenerator(int initial, PrefixGenerator prefixGenerator) {
        this.initial = initial;
        this.prefixGenerator = prefixGenerator;
    }

    /**
     * Another Spring-ambiguous ctor.  A bean will set the other properties.
     * @param initial
     */
    public SequenceGenerator(int initial) {
        this.initial = initial;
    }

    /**
     * Set the initial counter.
     * @param initial an integer.
     */
    public void setInitial(int initial) {
        this.initial = initial;
    }

    @Mandatory
    public void setSuffixes(Set<Integer> suffixes) {
        if (this.suffixes != null) {
            // release any contained objects
            this.suffixes.clear();
        }
        this.suffixes = suffixes;
    }

    /**
     * Print a dot delimited list of properties.
     * @return A String representation of the instance in this form:  prefix.initial.suffix.
     */
    @Override
    public synchronized String toString() {
        StringBuilder buffy = new StringBuilder();
        String prefix = prefixGenerator.getPrefix();
        buffy.append(prefix).append(".");
        buffy.append(initial + counter++).append(".");
        // handle suffixes collection
        DecimalFormat format = new DecimalFormat("0000");
        int counter = 0;
        for (Object suffix : suffixes) {
            if (counter++ > 0)
                buffy.append("-");
            String formattedSuffix = format.format((Integer)suffix);
            buffy.append(formattedSuffix);
        }
        return buffy.toString();
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) // reflexive equality
            return true;
        if (object == null || !(getClass().equals(object.getClass())))
            return false;
        // so far so good
        SequenceGenerator sequenceGenerator = (SequenceGenerator)object;
        // all POJO properties must be set in the container and so must not be null (so no checking)
        boolean result =
                sequenceGenerator.initial == this.initial
                && sequenceGenerator.counter == this.counter
                && sequenceGenerator.prefixGenerator.equals(this.prefixGenerator)
                && sequenceGenerator.suffixes.equals(this.suffixes);
        return result;
    }

}
