package com.sharath.parsam.model;

import java.util.Map;

public class DecodedJwtHolder implements JwtParts {
    private final Map<String, Object> header;
    private final Map<String, Object> payload;
    private final byte[] signature;

    public DecodedJwtHolder(
            Map<String, Object> header,
            Map<String, Object> payload,
            byte[] signature) {
        this.header = header;
        this.payload = payload;
        this.signature = signature;
    }

    public Map<String, Object> getHeader() {
        return header;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public byte[] getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return "Header: " + header.toString() + " Payload: " + payload.toString() + " Masked Signature: *****";
    }

}
