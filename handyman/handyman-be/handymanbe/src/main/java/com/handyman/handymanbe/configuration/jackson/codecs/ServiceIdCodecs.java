package com.handyman.handymanbe.configuration.jackson.codecs;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.handyman.handymanbe.domain.service.ServiceId;
import com.handyman.handymanbe.domain.technician.TechnicianId;

import java.io.IOException;

public class ServiceIdCodecs {
    public static class Serializer extends JsonSerializer<ServiceId> {
        @Override
        public void serialize(ServiceId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.toString());
        }
    }

    public static class Deserializer extends JsonDeserializer<ServiceId> {
        @Override
        public ServiceId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            return ServiceId.fromString(p.getValueAsString());
        }
    }
}
