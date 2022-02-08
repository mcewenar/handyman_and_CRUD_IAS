package com.handyman.handymanbe.model.report;

import com.handyman.handymanbe.domain.report.Report;

public class CreateReportOutput {
    private Report report;

    public CreateReportOutput() {
    }

    public CreateReportOutput(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

}
