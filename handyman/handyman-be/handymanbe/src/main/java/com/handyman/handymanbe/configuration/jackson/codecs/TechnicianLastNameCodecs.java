package com.handyman.handymanbe.configuration.jackson.codecs;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.handyman.handymanbe.domain.technician.TechnicianLastName;
import com.handyman.handymanbe.domain.technician.TechnicianName;

import java.io.IOException;

public class TechnicianLastNameCodecs {

    public static class Serializer extends JsonSerializer<TechnicianLastName> {
        @Override
        public void serialize(TechnicianLastName value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.toString());
        }
    }

    public static class Deserializer extends JsonDeserializer<TechnicianLastName> {
        @Override
        public TechnicianLastName deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            return new TechnicianLastName(p.getValueAsString());
        }
    }
}

