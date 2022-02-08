package com.handyman.handymanbe.domain.technician;

import java.util.Objects;

public class Technician {
    private final TechnicianId id;
    private final TechnicianName name;
    private final TechnicianLastName lastName;


    public Technician(TechnicianId id, TechnicianName name, TechnicianLastName lastName) {
        Objects.requireNonNull(id,"Technician id can not be null");
        Objects.requireNonNull(name,"Technician name can not be null");
        Objects.requireNonNull(lastName,"Technician last name can not be null");

        this.id = id;
        this.name = name;
        this.lastName = lastName;
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
}
