package com.lionfish.robo_clipping_kindle.service;

import com.lionfish.robo_clipping_kindle.domain.request.AuthenticationRequest;
import com.lionfish.robo_clipping_kindle.util.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AuthenticationServiceTest {

    @Test
    void getToken(){
        AuthenticationRequest request = new AuthenticationRequest("user", "password");

        String token =  AuthenticationService.getToken(request);
        Assertions.assertNotNull(token);
        Assertions.assertTrue(JWTUtil.validateToken(token));
        Assertions.assertEquals("user", JWTUtil.getUsername(token));

        AuthenticationRequest invalidRequest = new AuthenticationRequest(null, "password");
        String noUserToken =  AuthenticationService.getToken(invalidRequest);
        Assertions.assertNotNull(noUserToken);
        Assertions.assertTrue(JWTUtil.validateToken(noUserToken));
        Assertions.assertNull(JWTUtil.getUsername(noUserToken));
    }
}
