package com.apress.springrecipes.hello;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Date: 1/18/11
 * Time: 10:09 PM
 */
public class HelloWorld {

    private static Logger logger;
    static {
        logger = Logger.getLogger(HelloWorld.class);
    }

    private String message;
    private List<Holiday> holidays;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }

    public void hello() {
        StringBuilder buffy = new StringBuilder("\nHello, ");
        buffy.append(message).append("\n");
        for (Holiday holiday : holidays) {
            buffy.append("Today is ");
            buffy.append(holiday.getDay());
            buffy.append("/").append(holiday.getMonth()).append(".  ");
            buffy.append(holiday.getGreeting()).append(".\n");
        }
        logger.info(buffy);
        buffy.delete(0, buffy.length());
    }

}
