package com.sharath.parsam.model;

public interface JwtParts<T, U> {

    T getHeader();

    T getPayload();

    U getSignature();

}
