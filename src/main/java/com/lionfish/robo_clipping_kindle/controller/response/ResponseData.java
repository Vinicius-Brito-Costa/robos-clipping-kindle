package com.lionfish.robo_clipping_kindle.controller.response;

import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * Holds all important data to create a uniform Response
 */
@Generated
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    
    @Getter @Setter
    private String code;

    @Getter @Setter
    private HttpStatus status;

    @Setter
    private String message;

    @Setter
    private Object body;

    public ResponseData(ResponseMap responseMap){
        setCode(responseMap.getCode());
        setStatus(responseMap.getStatus());
        setMessage(responseMap.getMessage());
    }

    public Object getBody(){
        if(this.body != null){
            return this.body;
        }
        return this.message;
    }
}
