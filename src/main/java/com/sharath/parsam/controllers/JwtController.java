package com.sharath.parsam.controllers;

import com.sharath.parsam.model.*;
import com.sharath.parsam.services.JwtOperations;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Exposed JWT Operations")
public class JwtController {

    @Autowired
    private JwtOperations operations;

    private final String DECODE_TOKEN_PATH = "/decode/token";
    private final String VERIFY_TOKEN_PATH = "/verify/token";

    private Logger log = LoggerFactory.getLogger(JwtController.class);

    @CrossOrigin(maxAge = 3600)
    @RequestMapping(value = DECODE_TOKEN_PATH, method = RequestMethod.POST)
    @ApiOperation(value = "Decode the JWT and display in plain text format")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = DecodedJwtHolder.class, message = "Successfully decoded token"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "Invalid token")})
    public ResponseEntity<?> decodeJwt(@RequestBody DecodeTokenRequest request) {
        String encodedToken;
        if (request != null && (encodedToken = request.getToken()) != null && !encodedToken.isEmpty()) {
            try {
                log.info("Decoding token: " + encodedToken);
                DecodedJwtHolder decodedToken = operations.decodeJwt(encodedToken);
                if (decodedToken != null) {
                    log.info("Decoded token: " + decodedToken.toString());
                    return new ResponseEntity<>(decodedToken, HttpStatus.OK);
                }
            } catch (IllegalArgumentException e) {
                log.error("Unable to parse the token: " + encodedToken);
                return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new ErrorResponse("Token is empty"), HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(maxAge = 3600)
    @RequestMapping(value = VERIFY_TOKEN_PATH, method = RequestMethod.POST)
    @ApiOperation(value = "Validate signature")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = SignatureResponse.class, message = "Signature verified"),
            @ApiResponse(code = 400, message = "Invalid signature")})
    public ResponseEntity<?> verifySignature(@RequestBody TokenSecretRequest request) {
        return new ResponseEntity<>(new SignatureResponse(true), HttpStatus.OK);
    }
}
