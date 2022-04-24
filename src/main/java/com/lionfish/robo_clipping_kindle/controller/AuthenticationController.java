package com.lionfish.robo_clipping_kindle.controller;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.command.ICommand;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseData;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseMap;
import com.lionfish.robo_clipping_kindle.domain.request.GetTokenRequestDTO;
import com.lionfish.robo_clipping_kindle.domain.request.ValidateTokenRequestDTO;
import com.lionfish.robo_clipping_kindle.domain.response.GetTokenResponseDTO;
import com.lionfish.robo_clipping_kindle.service.AuthenticationService;
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

    ICommand responseCommand = CommandMapEnum.getCommandClass("response").getCommandClass();

    /***
     * Generate a JWT token using a user/password
     * @return a new bearer token
     */
    @PostMapping("/get-token")
    public Object getToken(@RequestBody  GetTokenRequestDTO tokenRequest){

        ResponseData responseData;

        String token = AuthenticationService.getToken(tokenRequest);
        if(token != null){
            responseData = new ResponseData(ResponseMap.OK);
            responseData.setBody(new GetTokenResponseDTO(token));
        }
        else {
            responseData = new ResponseData(ResponseMap.BAD_REQUEST);
            responseData.setBody("Invalid credentials.");
        }

        return responseCommand.execute(responseData);
    }

    /***
     * Verify if the provided token is valid
     * @param token Bearer token
     * @return token is valid or not
     */
    @PostMapping("/validate-token")
    public Object validateToken(@RequestBody ValidateTokenRequestDTO token){

        boolean isTokenValid = AuthenticationService.validateToken(token);
        ResponseData responseData = new ResponseData(isTokenValid ? ResponseMap.OK : ResponseMap.BAD_REQUEST);
        responseData.setBody(isTokenValid ? token.getToken() : "Invalid token");

        return responseCommand.execute(responseData);
    }
}
