package com.handyman.handymanbe.repository.report;

import com.handyman.handymanbe.domain.report.Report;
import com.handyman.handymanbe.domain.technician.TechnicianId;

import java.util.List;

public interface ReportRepository {
    List<Report> list();

    void create(Report report);

    List<Report> getAllFromTechnicianByWeek(Integer week, TechnicianId id);
}
