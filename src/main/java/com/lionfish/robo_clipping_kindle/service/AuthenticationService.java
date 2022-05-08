package com.lionfish.robo_clipping_kindle.service;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.domain.command.Command;
import com.lionfish.robo_clipping_kindle.domain.request.AuthenticationRequest;
import com.lionfish.robo_clipping_kindle.domain.response.IntegrationAuthenticationResponse;
import com.lionfish.robo_clipping_kindle.util.JWTUtil;
import com.lionfish.robo_clipping_kindle.validator.CommandValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private static final int DEFAULT_TOKEN_EXPIRATION_TIME_IN_SECONDS = 30;

    private AuthenticationService(){}

    public static IntegrationAuthenticationResponse integrationAuthentication(String integration){
        Command command = CommandMapEnum.getCommandClass("auth-" + integration);
        CommandValidator validator = new CommandValidator();
        if(validator.validate(command)){
            return (IntegrationAuthenticationResponse) command.getCommandClass().execute(null);
        }
        return null;
    }

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
