package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.controller.response.ResponseData;
import com.lionfish.robo_clipping_kindle.controller.response.ResponseMap;

import com.lionfish.robo_clipping_kindle.domain.response.ResponseDAO;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ResponseCommand implements ICommand {

    @Override
    public ResponseEntity<Object> execute(Object object) {
        ResponseData resData = (ResponseData) object;
        return ResponseEntity
            .status(resData.getStatus())
            .body(new ResponseDAO(resData.getCode(), resData.getBody()));
    }
}
