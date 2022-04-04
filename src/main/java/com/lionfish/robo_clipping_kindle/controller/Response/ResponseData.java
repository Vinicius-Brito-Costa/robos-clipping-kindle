package com.lionfish.robo_clipping_kindle.controller.Response;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
/**
 * Holds all important data to create a Response
 */
public class ResponseData {
    
    @Getter @Setter
    private String code;

    @Getter @Setter
    private HttpStatus status;

    @Setter
    private String message;

    @Setter
    private Object body;

    public Object getBody(){
        if(this.body != null){
            return this.body;
        }
        return this.message;
    }
}
