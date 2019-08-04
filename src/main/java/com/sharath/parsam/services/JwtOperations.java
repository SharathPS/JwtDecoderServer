package com.sharath.parsam.services;

import com.sharath.parsam.model.DecodedJwtHolder;

public interface JwtOperations {

    DecodedJwtHolder decodeJwt(String encodedToken);

}
