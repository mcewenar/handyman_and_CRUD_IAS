package com.handyman.handymanbe.controllers;

import com.handyman.handymanbe.domain.report.Report;

import com.handyman.handymanbe.domain.technician.Technician;
import com.handyman.handymanbe.model.report.CreateReportInput;
import com.handyman.handymanbe.model.report.CreateReportOutput;
import com.handyman.handymanbe.services.ReportService;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value="/report")
public class ReportController {

    private final ReportService services;

    public ReportController(ReportService services) {
        this.services = services;
    }

    @GetMapping
    //Return a technician list
    public List<Report> listReports() {
        return services.listReports();
    }
    //Post decorator for create any product from Clientside

    @PostMapping
    public CreateReportOutput createReport(@RequestBody CreateReportInput input) {
        String technicianId = input.getTechnicianId();
        String serviceId = input.getServiceId();
        LocalDateTime initDate = input.getInitDate();
        LocalDateTime endDate = input.getEndDate();

        Report report = new Report(technicianId, serviceId, initDate, endDate);
        Report createdReport = services.createReport(report);

        return new CreateReportOutput(createdReport);
    }
}


