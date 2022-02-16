package com.handyman.handymanbe.domain.service;


import java.util.Objects;

public class Service {
    private final Type type;
    private final ServiceId serviceId;

    public Service(Type type, ServiceId serviceId) {
        Objects.requireNonNull(serviceId, "Service id can not be null");
        Objects.requireNonNull(type, "Service type can not be null");

        this.type = type;
        this.serviceId = serviceId;
    }
}
