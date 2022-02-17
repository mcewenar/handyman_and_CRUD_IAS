package com.handyman.handymanbe.repository.technician;

import com.handyman.handymanbe.domain.report.Report;
import com.handyman.handymanbe.domain.technician.Technician;
import com.handyman.handymanbe.domain.technician.TechnicianId;

import java.util.List;

public interface TechnicianRepository {
    List<Technician> list();

    Technician findOne(TechnicianId id);

    void create(Technician technician);

    void update(TechnicianId id, Technician technician);

    void delete(TechnicianId id);

    List<Report> getAllReports(TechnicianId technicianId);
}
