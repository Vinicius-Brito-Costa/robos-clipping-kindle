package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.domain.response.ResponseData;

import com.lionfish.robo_clipping_kindle.domain.response.DefaultResponse;
import org.springframework.http.ResponseEntity;

public class ResponseCommand implements ICommand {

    @Override
    public ResponseEntity<DefaultResponse> execute(Object object) {
        ResponseData resData = (ResponseData) object;
        return ResponseEntity
            .status(resData.getStatus())
            .body(new DefaultResponse(resData.getCode(), resData.getBody()));
    }
}
