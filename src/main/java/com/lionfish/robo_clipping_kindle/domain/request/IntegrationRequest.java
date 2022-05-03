package com.lionfish.robo_clipping_kindle.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntegrationRequest {
    private String token;
    private String clientToken;
    private String clippings;
}
