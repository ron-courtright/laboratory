package com.apress.springrecipes.report;

/**
 * Date: 1/18/11
 * Time: 11:47 AM
 */
public class ServiceLocator {
    private static Container container = new Container();

    public static ReportGenerator getReportGenerator() {
        ReportGenerator reportGenerator = (ReportGenerator)container.getComponent("reportGenerator");
        return reportGenerator;
    }
}
