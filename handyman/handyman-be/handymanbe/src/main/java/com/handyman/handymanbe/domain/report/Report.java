package com.handyman.handymanbe.domain.report;

import com.handyman.handymanbe.domain.service.ServiceId;
import com.handyman.handymanbe.domain.technician.TechnicianId;

import java.time.LocalDateTime;
import java.util.Objects;

public class Report {
    //private final TechnicianId technicianId;
    //private final ServiceId serviceId;
    private final String technicianId;
    private final String serviceId;
    private final LocalDateTime initDate;
    private final LocalDateTime endDate;

    public Report(String technicianId, String serviceId, LocalDateTime initDate, LocalDateTime endDate) {
        Objects.requireNonNull(technicianId, "Technician id can not be null");
        Objects.requireNonNull(serviceId, "Service id name can not be null");
        Objects.requireNonNull(initDate, "Report init birthday can not be null");
        Objects.requireNonNull(endDate, "Report end birthday can not be null");
        this.technicianId = technicianId;
        this.serviceId = serviceId;
        this.initDate = initDate;
        this.endDate = endDate;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

}
