package com.sharath.parsam.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

public interface ClaimsMaker {


     static Optional<Map<String, Object>> getClaims(String encodedValue) {
        Base64.Decoder urlDecoder = Base64.getUrlDecoder();
        byte[] decode = urlDecoder.decode(encodedValue);
        if (decode != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Map<String, Object> o = mapper.readValue(decode, new TypeReference<Map<String, Object>>() {
                });
                return Optional.of(o);
            } catch (IOException e) {
                throw new IllegalArgumentException("Invalid token");
            }
        }
        return Optional.empty();
    }
}
