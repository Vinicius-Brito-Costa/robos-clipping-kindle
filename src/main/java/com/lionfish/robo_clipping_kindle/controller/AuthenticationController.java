package com.lionfish.robo_clipping_kindle.controller;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.command.ICommand;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseData;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseMap;
import com.lionfish.robo_clipping_kindle.domain.response.GetTokenResponseDTO;
import com.lionfish.robo_clipping_kindle.domain.request.GetTokenRequestDTO;
import com.lionfish.robo_clipping_kindle.domain.request.ValidateTokenRequestDTO;
import com.lionfish.robo_clipping_kindle.domain.response.ValidateTokenResponseDTO;
import com.lionfish.robo_clipping_kindle.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/authentication",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    ICommand responseCommand = CommandMapEnum.getCommandClass("response");
    private static final int DEFAULT_TOKEN_EXPIRATION_TIME_IN_SECONDS = 30;
    private static final String APPLICATION_LOGIN = "user";
    private static final String APPLICATION_PASSWORD = "password";

    /***
     * Generate a JWT token using a user/password
     * @return a new bearer token
     */
    @PostMapping("/get-token")
    public Object getToken(@RequestBody  GetTokenRequestDTO getTokenRequestDTO){

        ResponseData responseData;

        // Validate login data
        // TODO: Replace with correct credential validation
        if(!(APPLICATION_LOGIN.equals(getTokenRequestDTO.getUser()) && APPLICATION_PASSWORD.equals(getTokenRequestDTO.getPassword()))){
            logger.error("[ Error ] Invalid credentials.");
            responseData = new ResponseData(ResponseMap.BAD_REQUEST);
            responseData.setBody("Invalid credentials.");
            return responseCommand.execute(responseData);
        }
        logger.info("[ Message ] Correct credentials.");

        // Generate token
        String token = "Bearer " + JWTUtil.generateJWT(getTokenRequestDTO.getUser(), "export", DEFAULT_TOKEN_EXPIRATION_TIME_IN_SECONDS);
        logger.info("[ Message ] Token generated.");
        responseData = new ResponseData(ResponseMap.OK);
        responseData.setBody(new GetTokenResponseDTO(token));

        return responseCommand.execute(responseData);
    }

    /***
     * Verify if the provided token is valid
     * @param token Bearer token
     * @return token is valid or not
     */
    @PostMapping("/validate-token")
    public Object validateToken(@RequestBody ValidateTokenRequestDTO token){

        ValidateTokenResponseDTO validToken = new ValidateTokenResponseDTO(token.getToken(), JWTUtil.validateToken(token.getToken()));
        boolean isTokenValid = validToken.isValid();
        ResponseData responseData = new ResponseData(isTokenValid ? ResponseMap.OK : ResponseMap.BAD_REQUEST);
        responseData.setBody(isTokenValid ? validToken : "Invalid token");

        return responseCommand.execute(responseData);
    }
}
