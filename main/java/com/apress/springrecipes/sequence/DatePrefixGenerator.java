package com.apress.springrecipes.sequence;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Date: 1/19/11
 * Time: 1:20 PM
 */
public class DatePrefixGenerator implements PrefixGenerator {

    private DateFormat formatter;

    public void setPattern(String pattern) {
        this.formatter = new SimpleDateFormat(pattern);
    }

    public String getPrefix() {
        long time = Calendar.getInstance().getTimeInMillis();
        Date date = new Date(time);
        return formatter.format(date);
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (object == null || !(getClass().equals(object.getClass())))
            return false;
        // so far so good
        DatePrefixGenerator datePrefixGenerator = (DatePrefixGenerator)object;
        boolean result = formatter.equals(datePrefixGenerator.formatter);
        return result;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return formatter != null ? formatter.hashCode() : 0;
    }

}
