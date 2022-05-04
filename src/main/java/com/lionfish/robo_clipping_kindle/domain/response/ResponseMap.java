package com.lionfish.robo_clipping_kindle.domain.response;

import lombok.Generated;
import org.springframework.http.HttpStatus;

import lombok.Getter;

/***
 * Contains the default ResponseData {@link ResponseData}
 */
@Getter
@Generated
public enum ResponseMap {

    OK("00", HttpStatus.OK, "Request successfully processed."),
    ACCEPTED("01", HttpStatus.ACCEPTED, "Request successfully accepted."),
    CREATED("02", HttpStatus.CREATED, "Created successfully."),
    NO_CONTENT("03", HttpStatus.NO_CONTENT, "Request successfull, there's no data to be returned."),
    CONTINUE("100", HttpStatus.CONTINUE, "Request will be processed."),
    BAD_REQUEST("101", HttpStatus.BAD_REQUEST, "Malformed payload."),
    BAD_GATEWAY("102", HttpStatus.BAD_GATEWAY, "Error while trying to load request.");

    @Getter
    private final String code;
    @Getter
    private final HttpStatus status;
    @Getter
    private final String message;

    ResponseMap(String code, HttpStatus status, String defaultMessage){
        this.code = code;
        this.status = status;
        this.message = defaultMessage;
    }
}
