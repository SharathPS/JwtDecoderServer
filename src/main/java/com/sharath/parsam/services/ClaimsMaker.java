package com.sharath.parsam.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Component
public class ClaimsMaker {

    private final ObjectMapper MAPPER = new ObjectMapper();

    public Optional<Map<String, Object>> getClaims(String encodedValue) {
        Base64.Decoder urlDecoder = Base64.getUrlDecoder();
        byte[] decode = urlDecoder.decode(encodedValue);
        if (decode != null) {
            try {
                Map<String, Object> o = MAPPER.readValue(decode, new TypeReference<Map<String, Object>>() {
                });
                return Optional.of(o);
            } catch (IOException e) {
                throw new IllegalArgumentException("Invalid token");
            }
        }
        return Optional.empty();
    }
}
