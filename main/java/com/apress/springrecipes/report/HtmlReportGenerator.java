package com.apress.springrecipes.report;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * Date: 1/18/11
 * Time: 11:17 AM
 */
public class HtmlReportGenerator implements ReportGenerator {

    private static Logger logger;
    static {
        logger = Logger.getLogger(HtmlReportGenerator.class);
    }

    public void generate(String[][] table) {
        logger.info("Generating HTML report...");
    }
}
