package com.sharath.parsam.model;

public class SignatureResponse {

    private final boolean verified;

    public SignatureResponse(boolean verified) {
        this.verified = verified;
    }

    public boolean isVerified() {
        return verified;
    }
}
