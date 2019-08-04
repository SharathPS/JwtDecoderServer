package com.sharath.parsam.services;

import com.sharath.parsam.model.DecodedJwtHolder;
import com.sharath.parsam.model.EncodedJwtHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

@Service
public class JwtOperationImpl implements JwtOperations {

    @Autowired
    private ClaimsMaker claimsMaker;

    @Override
    public DecodedJwtHolder decodeJwt(String encodedToken) {

        if (encodedToken != null && !encodedToken.isEmpty()) {
            EncodedJwtHolder encodedJwt = EncodedJwtHolder.encodedJwt(encodedToken)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Token"));
            if (encodedJwt != null) {
                return new DecodedJwtHolder(makeClaims(encodedJwt.getHeader()),
                        makeClaims(encodedJwt.getPayload()), makeSignature(encodedJwt.getSignature()));
            }
        }

        throw new IllegalArgumentException("Invalid Token");
    }

    private Map<String, Object> makeClaims(String tokenPart) {
        return claimsMaker.getClaims(tokenPart)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Token"));
    }

    private byte[] makeSignature(String tokenPart) {
        Base64.Decoder urlDecoder = Base64.getUrlDecoder();
        return urlDecoder.decode(tokenPart);
    }
}
