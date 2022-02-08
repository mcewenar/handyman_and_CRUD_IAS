package com.handyman.handymanbe.model.technician;

import com.handyman.handymanbe.domain.technician.TechnicianId;
import com.handyman.handymanbe.domain.technician.TechnicianLastName;
import com.handyman.handymanbe.domain.technician.TechnicianName;

import java.time.LocalDateTime;

public class CreateTechnicianInput {
    private String name;
    private String lastName;

    public CreateTechnicianInput() {
    }

    public CreateTechnicianInput(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
