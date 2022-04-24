package com.lionfish.robo_clipping_kindle.service;

import com.lionfish.robo_clipping_kindle.domain.request.GetTokenRequestDTO;
import com.lionfish.robo_clipping_kindle.domain.request.ValidateTokenRequestDTO;
import com.lionfish.robo_clipping_kindle.domain.response.ValidateTokenResponseDTO;
import com.lionfish.robo_clipping_kindle.util.EnvironmentUtil;
import com.lionfish.robo_clipping_kindle.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
    private static final int DEFAULT_TOKEN_EXPIRATION_TIME_IN_SECONDS = 30;
    private static final String APPLICATION_LOGIN = "ROBO_CLIPPING_USER";
    private static final String APPLICATION_PASSWORD = "ROBO_CLIPPING_PASSWORD";

    private AuthenticationService(){}

    public static boolean validCredentials(String user, String password){

        // TODO: Replace with correct credential validation
        String envUser = EnvironmentUtil.getEnvVariable(APPLICATION_LOGIN);
        String envPassword = EnvironmentUtil.getEnvVariable(APPLICATION_PASSWORD);
        if(envUser == null || envPassword == null){
            return false;
        }

        return envUser.equals(user) && envPassword.equals(password);
    }

    public static String getToken(GetTokenRequestDTO tokenRequest){

        // Validate login data
        if(!validCredentials(tokenRequest.getUser(), tokenRequest.getPassword())){
            logger.error("[ Error ] Invalid credentials.");
            return null;
        }
        logger.info("[ Message ] Correct credentials.");

        // Generate token
        String token = "Bearer " + JWTUtil.generateJWT(tokenRequest.getUser(), "export", DEFAULT_TOKEN_EXPIRATION_TIME_IN_SECONDS);
        logger.info("[ Message ] Token generated.");
        return token;
    }

    public static boolean validateToken(ValidateTokenRequestDTO validateTokenRequest){
        ValidateTokenResponseDTO validToken = new ValidateTokenResponseDTO(validateTokenRequest.getToken(), JWTUtil.validateToken(validateTokenRequest.getToken()));
        return validToken.isValid();
    }
}
