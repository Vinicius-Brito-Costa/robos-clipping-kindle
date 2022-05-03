package com.lionfish.robo_clipping_kindle.service;

import com.lionfish.robo_clipping_kindle.domain.request.AuthenticationRequest;
import com.lionfish.robo_clipping_kindle.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private static final int DEFAULT_TOKEN_EXPIRATION_TIME_IN_SECONDS = 30;

    private AuthenticationService(){}

    public static boolean validCredentials(String user, String password, AuthenticationManager authenticationManager){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user, password));
        return authentication.isAuthenticated();
    }

    public static String getToken(AuthenticationRequest tokenRequest){
        // Generate token
        String token = "Bearer " + JWTUtil.generateJWT(tokenRequest.getUser(), "export", DEFAULT_TOKEN_EXPIRATION_TIME_IN_SECONDS);
        logger.info("[ Message ] Token generated.");
        return token;
    }
}
