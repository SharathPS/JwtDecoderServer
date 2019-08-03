package com.sharath.parsam.controllers;

import com.sharath.parsam.model.DecodeTokenRequest;
import com.sharath.parsam.model.DecodedJwtHolder;
import com.sharath.parsam.model.ErrorResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Exposed JWT Operations")
public class JwtController {

    @Autowired
    private JwtOperations operations;

    private final String DECODE_TOKEN_PATH = "/decode/token";

    Logger log = LoggerFactory.getLogger(JwtController.class);

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
                    return new ResponseEntity<>(decodedToken, HttpStatus.ACCEPTED);
                }
            } catch (IllegalArgumentException e) {
                log.error("Unable to parse the token: " + encodedToken);
                return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(new ErrorResponse("Token is empty"), HttpStatus.BAD_REQUEST);
    }
}
