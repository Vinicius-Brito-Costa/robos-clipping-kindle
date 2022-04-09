package com.lionfish.robo_clipping_kindle.command;

import com.lionfish.robo_clipping_kindle.controller.Response.ResponseMap;

import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

public class ResponseCommand implements ICommand {

    @Override
    public ResponseEntity<Object> execute(Object object) {
        ResponseMap map = (ResponseMap) object;
        return ResponseEntity
            .status(map.getResponse().getStatus())
            .body(new ResponseDAO(map.getResponse().getCode(), map.getResponse().getBody()));
    }
    
    @Data
    @AllArgsConstructor
    class ResponseDAO{
        private String code;
        private Object data;
    }
}
