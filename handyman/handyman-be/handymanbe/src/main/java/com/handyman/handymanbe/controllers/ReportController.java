package com.handyman.handymanbe.controllers;

import com.handyman.handymanbe.domain.report.Report;

import com.handyman.handymanbe.domain.service.ServiceId;
import com.handyman.handymanbe.domain.technician.Technician;
import com.handyman.handymanbe.domain.technician.TechnicianId;
import com.handyman.handymanbe.domain.workedHours.WorkedHours;
import com.handyman.handymanbe.model.report.CreateReportInput;
import com.handyman.handymanbe.model.report.CreateReportOutput;
import com.handyman.handymanbe.services.ReportService;
import com.handyman.handymanbe.services.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

//Response Entity: https://www.baeldung.com/spring-response-entity
@RestController
@RequestMapping(value="/report")
public class ReportController {

    @Autowired
    private TechnicianService technicianService;

    private final ReportService services;

    public ReportController(ReportService services) {
        this.services = services;
    }

    @GetMapping
    //Return a technician list
    public List<Report> listReports() {
        return services.listReports();
    }

    @PostMapping
    public CreateReportOutput createReport(@RequestBody CreateReportInput input) {
        TechnicianId technicianId = TechnicianId.fromString(input.getTechnicianId());
        ServiceId serviceId = ServiceId.fromString(input.getServiceId());
        LocalDateTime initDate = input.getInitDate();
        LocalDateTime endDate = input.getEndDate();

        Report report = new Report(technicianId, serviceId, initDate, endDate);
        Report createdReport = services.createReport(report);

        return new CreateReportOutput(createdReport);
    }
    //Other way with ResponseEntity:
    @GetMapping("/query/{id}/{week}")
    public ResponseEntity<WorkedHours> getWorkedHoursByTechnicianAndWeek(
            @PathVariable("id") String idTechnician,
            @PathVariable("week") Integer week) {

        TechnicianId technicianId = TechnicianId.fromString(idTechnician);
        Technician foundTechnician = technicianService.getTechnician(technicianId);

        if (foundTechnician == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(services.calculateHoursOfWorkForTechnician(week,technicianId),HttpStatus.OK);
        }
    }
}


