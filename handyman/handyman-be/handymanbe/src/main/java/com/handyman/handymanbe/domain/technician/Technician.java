package com.handyman.handymanbe.domain.technician;

import com.handyman.handymanbe.domain.report.Report;
import com.handyman.handymanbe.domain.workedHours.WorkedHours;

import java.util.List;
import java.util.Objects;

public class Technician {
    private final TechnicianId id;
    private final TechnicianName name;
    private final TechnicianLastName lastName;
    //private final List<Report> reports;


    public Technician(TechnicianId id, TechnicianName name, TechnicianLastName lastName) {
        Objects.requireNonNull(id,"Technician id can not be null");
        Objects.requireNonNull(name,"Technician name can not be null");
        Objects.requireNonNull(lastName,"Technician last name can not be null");
        //Objects.requireNonNull(reports,"Report list can not be null");

        this.id = id;
        this.name = name;
        this.lastName = lastName;
        //this.reports = reports;
    }

    public TechnicianId getId() {
        return id;
    }

    public TechnicianName getName() {
        return name;
    }

    public TechnicianLastName getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Technician{" +
                "id=" + id +
                ", name=" + name +
                ", lastName=" + lastName +
                '}';
    }

    public void WorkedHoursCalculate(List<Report> reports) {

    }




}
