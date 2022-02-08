package com.handyman.handymanbe.domain.service;

import com.handyman.handymanbe.domain.technician.TechnicianId;

import java.util.Objects;
import java.util.UUID;


public class ServiceId {
    private final UUID value;

    public ServiceId(UUID value) {
        Objects.requireNonNull(value, "Service id can not be null");
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
