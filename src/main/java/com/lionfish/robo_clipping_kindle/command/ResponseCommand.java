package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.controller.response.ResponseData;

import com.lionfish.robo_clipping_kindle.domain.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

public class ResponseCommand implements ICommand {

    @Override
    public ResponseEntity<Object> execute(Object object) {
        ResponseData resData = (ResponseData) object;
        return ResponseEntity
            .status(resData.getStatus())
            .body(new ResponseDTO(resData.getCode(), resData.getBody()));
    }
}
