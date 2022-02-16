package com.handyman.handymanbe.domain.technician;

import java.util.Objects;
import java.util.UUID;

public class TechnicianId {
    private final UUID value;

    public TechnicianId(UUID value) {
        Objects.requireNonNull(value, "Technician id can not be null");
        //REGEX (MEJORAR)
        this.value = value;
    }

    public static TechnicianId fromString(String unsafeValue) {
        return new TechnicianId(UUID.fromString(unsafeValue));
    }

    public static TechnicianId random() {
        return new TechnicianId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
