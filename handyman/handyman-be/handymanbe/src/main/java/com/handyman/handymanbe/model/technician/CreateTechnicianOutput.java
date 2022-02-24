package com.handyman.handymanbe.model.technician;

import com.handyman.handymanbe.domain.technician.Technician;
import com.handyman.handymanbe.domain.technician.TechnicianId;

public class CreateTechnicianOutput {
    private Technician technician;

    public CreateTechnicianOutput() {
    }

    public CreateTechnicianOutput(Technician technician) {
        this.technician = technician;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setPerson(Technician technician) {
        this.technician = technician;
    }
}
