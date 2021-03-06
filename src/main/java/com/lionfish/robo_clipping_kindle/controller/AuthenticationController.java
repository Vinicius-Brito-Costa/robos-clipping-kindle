package com.lionfish.robo_clipping_kindle.controller;

import com.lionfish.robo_clipping_kindle.command.CommandMapEnum;
import com.lionfish.robo_clipping_kindle.command.ICommand;
import com.lionfish.robo_clipping_kindle.domain.request.AuthenticationRequest;
import com.lionfish.robo_clipping_kindle.domain.response.AuthenticationResponse;
import com.lionfish.robo_clipping_kindle.domain.response.IntegrationAuthenticationResponse;
import com.lionfish.robo_clipping_kindle.domain.response.ResponseData;
import com.lionfish.robo_clipping_kindle.domain.response.ResponseMap;
import com.lionfish.robo_clipping_kindle.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/authentication",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    ICommand responseCommand = CommandMapEnum.getCommandClass("internal-response").getCommandClass();

    /***
     * Generate a JWT token using a user/password
     * @return a new bearer token
     */
    @CrossOrigin
    @PostMapping("/authenticate")
    public Object getToken(@RequestBody AuthenticationRequest tokenRequest){

        ResponseData responseData;

        if(AuthenticationService.validCredentials(tokenRequest.getUser(), tokenRequest.getPassword(), authenticationManager)){
            String token = AuthenticationService.getToken(tokenRequest);
            responseData = new ResponseData(ResponseMap.OK);
            responseData.setBody(new AuthenticationResponse(token));
        }
        else {
            responseData = new ResponseData(ResponseMap.BAD_REQUEST);
            responseData.setBody("Invalid credentials.");
        }

        return responseCommand.execute(responseData);
    }

    @CrossOrigin
    @GetMapping("/integration/{integration}")
    public Object authenticateIntegration(@PathVariable("integration") String integration){
        ResponseData response = new ResponseData(ResponseMap.BAD_REQUEST);
        IntegrationAuthenticationResponse integrationResponse = AuthenticationService.integrationAuthentication(integration);
        if(integrationResponse != null){
            response = new ResponseData(ResponseMap.OK);
            response.setBody(integrationResponse);
        }

        return responseCommand.execute(response);
    }
}
