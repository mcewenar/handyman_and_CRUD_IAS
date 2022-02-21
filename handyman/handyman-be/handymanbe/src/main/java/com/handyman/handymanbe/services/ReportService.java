package com.handyman.handymanbe.services;

import com.handyman.handymanbe.domain.report.Report;
import com.handyman.handymanbe.domain.technician.Technician;
import com.handyman.handymanbe.domain.technician.TechnicianId;
import com.handyman.handymanbe.domain.workedHours.WorkedHours;
import com.handyman.handymanbe.repository.report.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private TechnicianService technicianService;

    public ReportRepository repository;
    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public Report createReport(Report report) {
        repository.create(report);
        return report;
    }

    public List<Report> listReports() {
        return repository.list();
    }

    public WorkedHours calculateHoursOfWorkForTechnician(Integer week, TechnicianId id){
        Technician technician = technicianService.getTechnician(id);
        List<Report> reports = repository.getAllFromTechnicianByWeek(week, id);
        WorkedHours workedHours = technician.WorkedHoursCalculate(reports);
        return workedHours;
    }
}
