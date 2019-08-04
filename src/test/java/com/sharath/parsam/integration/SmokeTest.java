package com.sharath.parsam.integration;

import com.sharath.parsam.controllers.JwtController;
import com.sharath.parsam.model.DecodeTokenRequest;
import com.sharath.parsam.model.DecodedJwtHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    private JwtController controller;

    @Test
    public void token_should_decode_when_valid() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        DecodeTokenRequest request = new DecodeTokenRequest();
        request.setToken(token);

        ResponseEntity<?> responseEntity = controller.decodeJwt(request);
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        DecodedJwtHolder body = (DecodedJwtHolder) responseEntity.getBody();
        assertNotNull(body);
        assertNotNull(body.getHeader());
        assertNotNull(body.getPayload());
        assertNotNull(body.getSignature());

    }
}
