package com.lionfish.robo_clipping_kindle.domain.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String user;
    private String password;
}