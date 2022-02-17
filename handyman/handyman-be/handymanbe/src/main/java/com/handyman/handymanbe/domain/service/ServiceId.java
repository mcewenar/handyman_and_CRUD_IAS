package com.handyman.handymanbe.domain.service;

import java.util.Objects;
import java.util.UUID;


public class ServiceId {
    private final UUID value;

    public ServiceId(UUID value) {
        Objects.requireNonNull(value, "Service id can not be null");
        this.value = value;
    }

    public static ServiceId fromString(String unsafeValue) {
        return new ServiceId(UUID.fromString(unsafeValue));
    }

    public static ServiceId random() {
        return new ServiceId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
