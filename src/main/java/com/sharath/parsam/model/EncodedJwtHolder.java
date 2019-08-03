package com.sharath.parsam.model;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EncodedJwtHolder implements JwtParts{

    private final String header;
    private final String payload;
    private final String signature;

    public EncodedJwtHolder(String header, String payload, String signature) {
        this.header = header;
        this.payload = payload;
        this.signature = signature;
    }

    public static Optional<EncodedJwtHolder> encodedJwt(String token) {
        if (token != null && token.length() > 0) {
            List<String> tokenParts = Arrays.stream(token.split("\\."))
                    .map(String::trim)
                    .collect(Collectors.toList());

            if (isBase64Value(tokenParts)) {
                if (tokenParts.size() == 3) {
                    return Optional.of(new EncodedJwtHolder(tokenParts.get(0), tokenParts.get(1), tokenParts.get(2)));
                }
                if (tokenParts.size() == 2) {
                    return Optional.of(new EncodedJwtHolder(tokenParts.get(0), tokenParts.get(1), null));
                }
            }
        }
        return Optional.empty();
    }

    private static boolean isBase64Value(List<String> tokenParts) {
        Base64.Decoder urlDecoder = Base64.getUrlDecoder();
        return tokenParts.stream()
                .allMatch(part -> {
                            try {
                                return urlDecoder.decode(part) != null;
                            } catch (IllegalArgumentException e) {
                                return false;
                            }
                        }
                );
    }

    public String getHeader() {
        return header;
    }

    public String getPayload() {
        return payload;
    }

    public String getSignature() {
        return signature;
    }

}
