package com.apress.springrecipes.sequence;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Good ol'fashioned JavaBean.
 * Date: 1/20/11
 * Time: 3:14 PM
 */
public class Sequence {

    private String id;
    private String prefix;
    private String suffix;

    public Sequence() {}

    public Sequence(String id, String prefix, String suffix) {
        this.id = id;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Mandatory
    public void setId(String id) {
        this.id = id;
    }

    public String getPrefix() {
        return this.prefix;
    }

    @Mandatory
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }

    @Mandatory
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sequence sequence = (Sequence) o;

        if (id != null ? !id.equals(sequence.id) : sequence.id != null) return false;
        if (prefix != null ? !prefix.equals(sequence.prefix) : sequence.prefix != null) return false;
        if (suffix != null ? !suffix.equals(sequence.suffix) : sequence.suffix != null) return false;

        return true;
    }

    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (suffix != null ? suffix.hashCode() : 0);
        return result;
    }

    /**
     * @see Object#toString() 
     */
    @Override
    public String toString() {
        StringBuilder buffy = new StringBuilder();
        String fieldDelimitter = ".";
        buffy.append(prefix).append(fieldDelimitter);
        buffy.append(id).append(fieldDelimitter);
        buffy.append(suffix);
        return buffy.toString();
    }

}
