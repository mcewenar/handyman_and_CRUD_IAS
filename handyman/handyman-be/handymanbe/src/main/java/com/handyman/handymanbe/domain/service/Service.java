package com.handyman.handymanbe.domain.service;


public class Service {
    private final Type type;
    private final ServiceId serviceId;

    public Service(Type type, ServiceId serviceId) {
        this.type = type;
        this.serviceId = serviceId;
    }
}
