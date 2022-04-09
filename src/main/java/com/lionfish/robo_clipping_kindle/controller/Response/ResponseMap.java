package com.lionfish.robo_clipping_kindle.controller.Response;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ResponseMap {

    OK("00", HttpStatus.OK, "Request successfully processed."),
    ACCEPTED("01", HttpStatus.ACCEPTED, "Request successfully accepted."),
    CREATED("02", HttpStatus.CREATED, "Created successfully."),
    NO_CONTENT("03", HttpStatus.NO_CONTENT, "Request successfull, there's no data to be returned."),
    BAD_REQUEST("101", HttpStatus.BAD_REQUEST, "Malformed payload."),
    BAD_GATEWAY("102", HttpStatus.BAD_GATEWAY, "Error while trying to load request.");

    private ResponseData response = new ResponseData();

    private ResponseMap(String code, HttpStatus status, String defaultMessage){
        this.response.setCode(code);
        this.response.setStatus(status);
        this.response.setMessage(defaultMessage);
    }

    public void setResponseDataBody(Object body){
        this.response.setBody(body);;
    }
}
