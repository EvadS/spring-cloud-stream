package com.se.sample;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class CustomerDeserializer extends JsonDeserializer<Map<String, String>> {

    @Override
    public Map<String, String> deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final ObjectMapper mapper = (ObjectMapper) p.getCodec();
        return mapper.readValue(p.getText(), new TypeReference<Map<String, String>>() {
        });
    }
}