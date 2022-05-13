package com.lionfish.robo_clipping_kindle.service;

import com.lionfish.robo_clipping_kindle.domain.command.CommandType;
import com.lionfish.robo_clipping_kindle.domain.request.IntegrationRequest;
import com.lionfish.robo_clipping_kindle.domain.response.ResponseData;
import org.springframework.stereotype.Service;

@Service
public class IntegrationService {
    private IntegrationService(){}
    public static ResponseData buildResponseMessage(CommandType type, String command, IntegrationRequest request){
        return CommandService.buildResponse(type, command, request);
    }
}
