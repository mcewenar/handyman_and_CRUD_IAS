package com.handyman.handymanbe.configuration;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.handyman.handymanbe.configuration.jackson.codecs.ServiceIdCodecs;
import com.handyman.handymanbe.configuration.jackson.codecs.TechnicianIdCodecs;
import com.handyman.handymanbe.configuration.jackson.codecs.TechnicianLastNameCodecs;
import com.handyman.handymanbe.configuration.jackson.codecs.TechnicianNameCodecs;
import com.handyman.handymanbe.domain.service.ServiceId;
import com.handyman.handymanbe.domain.technician.TechnicianId;
import com.handyman.handymanbe.domain.technician.TechnicianLastName;
import com.handyman.handymanbe.domain.technician.TechnicianName;

public class InternalModule extends SimpleModule {
    private static final String NAME = "InternalModule";

    public InternalModule() {
        super(NAME, Version.unknownVersion());

        //TECHNICIAN:
        addSerializer(TechnicianId.class, new TechnicianIdCodecs.Serializer());
        addDeserializer(TechnicianId.class, new TechnicianIdCodecs.Deserializer());

        addSerializer(TechnicianName.class, new TechnicianNameCodecs.Serializer());
        addDeserializer(TechnicianName.class, new TechnicianNameCodecs.Deserializer());

        addSerializer(TechnicianLastName.class, new TechnicianLastNameCodecs.Serializer());
        addDeserializer(TechnicianLastName.class, new TechnicianLastNameCodecs.Deserializer());


        //REPORT:

        //SERVICE:
        addSerializer(ServiceId.class, new ServiceIdCodecs.Serializer());
        addDeserializer(ServiceId.class, new ServiceIdCodecs.Deserializer());

    }
}
