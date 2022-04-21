package com.lionfish.robo_clipping_kindle.controller;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.command.ICommand;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseData;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseMap;
import com.lionfish.robo_clipping_kindle.domain.authentication.LoginData;
import com.lionfish.robo_clipping_kindle.util.JWTUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/authentication")
public class AuthenticationController {

    ICommand responseCommand = CommandMapEnum.getCommandClass("response");

    private static final int DEFAULT_TOKEN_EXPIRATION_TIME_IN_SECONDS = 30;
    private static final String APPLICATION_LOGIN = "user";
    private static final String APPLICATION_PASSWORD = "password";

    /***
     * Generate a JWT token using a user/password
     * @return
     */
    @PostMapping("/get-token")
    public Object getToken(LoginData loginData){
        String token = "Bearer ";
        // Validate login data
        // Generate token
        String jwt = JWTUtil.generateJWT(loginData.getUser(), "export", DEFAULT_TOKEN_EXPIRATION_TIME_IN_SECONDS);
        token += jwt;
        ResponseData responseData = new ResponseData(ResponseMap.OK);
        responseData.setBody(token);
        return responseCommand.execute(responseData);
    }

    @PostMapping("/validate-token")
    public Object validateToken(@RequestBody String token){
        return "Is token valid? " + JWTUtil.validateToken(token);
    }
}
