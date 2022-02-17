package com.handyman.handymanbe.domain.report;

import com.handyman.handymanbe.domain.service.ServiceId;
import com.handyman.handymanbe.domain.technician.TechnicianId;

import java.time.LocalDateTime;
import java.time.temporal.IsoFields;
import java.util.Objects;

public class Report {
    //private final String technicianId;
    //private final String serviceId;
    private final TechnicianId technicianId;
    private final ServiceId serviceId;
    private final LocalDateTime initDate;
    private final LocalDateTime endDate;
    private final Integer weekOfYear;

    public Report(TechnicianId technicianId, ServiceId serviceId, LocalDateTime initDate, LocalDateTime endDate) {
        Objects.requireNonNull(technicianId, "Technician id can not be null");
        Objects.requireNonNull(serviceId, "Service id name can not be null");
        Objects.requireNonNull(initDate, "Report init birthday can not be null");
        Objects.requireNonNull(endDate, "Report end birthday can not be null");

        //Checks if this date-time is after the specified date-time.
        if(!endDate.isAfter(initDate)) {
            throw new IllegalArgumentException("The end date cannot be less or equal than start date");
        }

        if(endDate.isEqual(initDate)) {
            throw new IllegalArgumentException("End date cannot be equal than init date");
        }

        if(initDate.getYear() != endDate.getYear() || initDate.getMonth() != endDate.getMonth() ||  initDate.getDayOfMonth() != endDate.getDayOfMonth()) {
            throw new IllegalArgumentException("End date cannot be equal on year, month or day");
        }

        this.technicianId = technicianId;
        this.serviceId = serviceId;
        this.initDate = initDate;
        this.endDate = endDate;
        //Get the week of year
        this.weekOfYear = initDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    public TechnicianId getTechnicianId() {
        return technicianId;
    }

    public ServiceId getServiceId() {
        return serviceId;
    }

    public LocalDateTime getInitDate() {
        return initDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Integer getWeekOfYear() {
        return weekOfYear;
    }
}
