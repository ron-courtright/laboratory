package com.apress.springrecipes.report;

/**
 * Created by IntelliJ IDEA.
 * User: rcourtright
 * Date: 1/18/11
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ReportService {
    private ReportGenerator reportGenerator;

    /**
     * Construct away!
     */
    public ReportService() {}

    public void setReportGenerator(ReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }

    public void generateAnnualReport(int year) {
        String[][] statistics = null;
        //
        // Gather year-end data
        //
        reportGenerator.generate(statistics);
    }

    public void generateMonthlyReport(int year, int month) {
        String[][] statistics = null;
        //
        // Gather month-end data
        //
        reportGenerator.generate(statistics);
    }

    public void generateDailyReport(int year, int month, int day) {
        String[][] statistics = null;
        //
        // Gather daily data
        //
        reportGenerator.generate(statistics);
    }
}
