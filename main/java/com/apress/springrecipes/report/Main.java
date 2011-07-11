package com.apress.springrecipes.report;

/**
 * Date: 1/18/11
 * Time: 11:39 AM
 */
public class Main {

    public static void main(String...args) {
        Container container = new Container();
        ReportService reportService = (ReportService)container.getComponent("reportService");
        reportService.generateAnnualReport(2011);
    }

}
