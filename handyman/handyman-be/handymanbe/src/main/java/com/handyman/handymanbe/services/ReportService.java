package com.handyman.handymanbe.services;

import com.handyman.handymanbe.domain.report.Report;
import com.handyman.handymanbe.repository.report.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    public ReportRepository repository;
    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

        public List<Report> listReports() {
        return repository.list();
    }

    public Report createReport(Report report) {
        repository.create(report);
        return report;
    }
}
