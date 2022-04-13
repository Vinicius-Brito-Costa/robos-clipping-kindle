package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.controller.response.ResponseMap;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ResponseCommand implements ICommand {

    @Override
    public ResponseEntity<Object> execute(Object object) {
        ResponseMap map = (ResponseMap) object;
        ResponseEntity<Object> response =  ResponseEntity
            .status(map.getResponse().getStatus())
            .body(new ResponseDAO(map.getResponse().getCode(), map.getResponse().getBody()));
        map.setResponseDataBody(null);
        return response;
    }
    
    @Data
    @AllArgsConstructor
    static class ResponseDAO{
        private String code;
        private Object data;
    }
}
