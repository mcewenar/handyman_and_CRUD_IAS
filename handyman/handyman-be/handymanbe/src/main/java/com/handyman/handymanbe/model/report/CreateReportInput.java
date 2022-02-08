package com.handyman.handymanbe.model.report;

import java.time.LocalDateTime;

public class CreateReportInput {
    private String technicianId;
    private String serviceId;
    private LocalDateTime initDate;
    private LocalDateTime endDate;

    public CreateReportInput() {
    }

    public CreateReportInput(String technicianId, String serviceId, LocalDateTime initDate, LocalDateTime endDate) {
        this.technicianId = technicianId;
        this.serviceId = serviceId;
        this.initDate = initDate;
        this.endDate = endDate;

    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDateTime initDate) {
        this.initDate = initDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
