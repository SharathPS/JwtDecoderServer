package com.sharath.parsam.services;

import com.sharath.parsam.model.DecodedJwtHolder;
import com.sharath.parsam.services.JwtOperationImpl;
import com.sharath.parsam.services.JwtOperations;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class JwtOperationsTest {

    @Test
    public void token_should_decode_when_valid() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        JwtOperations service = new JwtOperationImpl();
        DecodedJwtHolder responseEntity = service.decodeJwt(token);
        Assert.assertNotNull(responseEntity);
    }
}
