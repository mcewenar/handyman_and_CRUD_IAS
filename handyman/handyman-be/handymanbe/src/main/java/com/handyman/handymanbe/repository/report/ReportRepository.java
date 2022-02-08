package com.handyman.handymanbe.repository.report;

import com.handyman.handymanbe.domain.report.Report;

import java.util.List;

public interface ReportRepository {
    List<Report> list();
    void create(Report report);
}
